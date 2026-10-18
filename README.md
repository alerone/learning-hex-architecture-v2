# 🏰 GuildBoard — Arquitectura Hexagonal → Microservicios

> Proyecto de aprendizaje: un tablón de misiones de gremio que evoluciona capítulo a capítulo, desde un monolito hexagonal hasta un sistema de microservicios con eventos, resiliencia, configuración centralizada y API Gateway.

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F) ![Kafka](https://img.shields.io/badge/Kafka-4.3-black) ![Postgres](https://img.shields.io/badge/PostgreSQL-18-336791) ![Gradle](https://img.shields.io/badge/Gradle-multi--module-02303A)

---

## 🎯 Propósito

Practicar **arquitectura hexagonal (puertos y adaptadores)** y llevarla a un entorno **distribuido**. El dominio es simple a propósito (aventureros, misiones y oro) para que el foco esté en la arquitectura:

- Dominio puro, sin Spring ni JPA.
- Un servicio = 4 módulos Gradle con dependencias que solo apuntan hacia dentro.
- Comunicación **síncrona** (REST + circuit breaker) y **asíncrona** (Kafka).
- Reglas de arquitectura **verificadas por tests** (ArchUnit).

El PDF de referencia está en [`docs/hexagonal-architecture.pdf`](docs/hexagonal-architecture.pdf).

---

## 🗺️ Vista general

```
                         ┌────────────────────┐
   cliente ───────────▶  │  Gateway  :8080    │ ◀── config ─┐
                         └─────────┬──────────┘             │
          /api/quests/**  │  /api/adventurers/**  │  /api/treasury/**
                 ▼                 ▼                    ▼       │
          ┌────────────┐    ┌──────────────┐    ┌────────────┐  │
          │  Quests    │───▶│ Adventurers  │    │  Treasury  │  │
          │   :8081    │REST│    :8082     │    │   :8083    │  │
          └─────┬──────┘ +  └──────▲───────┘    └─────▲──────┘  │
                │  circuit        │                  │         │
                │  breaker        │   guildboard.quest-completed
                └───────── 📣 Kafka ─────────────────┘         │
                                                               │
          ┌─────────────────────┐   ┌──────────────┐           │
          │ Config Server :8888 │───┘  PostgreSQL  │ ◀─────────┘
          │  (config-repo/*.yml)│      :5432       │  (3 BBDD: quests / adventurers / treasury)
          └─────────────────────┘   └──────────────┘
```

| Componente | Puerto | Responsabilidad |
|---|---|---|
| **gateway** | 8080 | Punto de entrada único; enruta por prefijo a cada servicio |
| **quests** | 8081 | Ciclo de vida de las misiones (publicar → aceptar → completar) |
| **adventurers** | 8082 | Registro de aventureros, experiencia y rango |
| **treasury** | 8083 | Cuentas de oro y libro contable (*ledger*) |
| **config-server** | 8888 | Configuración centralizada desde `config-repo/` |
| **kafka** | 9092 | Bus de eventos |
| **postgres** | 5432 | Una base de datos por servicio |

---

## 🚀 Puesta en marcha paso a paso

**Requisitos:** JDK 21, Docker + Docker Compose, `curl` y `jq` (para los scripts).

Todos los comandos desde `guildboard/`:

```bash
cd guildboard
```

### 1️⃣ Infraestructura (Kafka + Postgres)

```bash
docker compose up -d
docker compose ps        # espera a que ambos estén "healthy"
```

`infra/postgres/init.sql` crea las bases `quests`, `adventurers` y `treasury` en el primer arranque.

### 2️⃣ Config Server (siempre primero)

```bash
./gradlew :config-server:bootRun
```

Los demás servicios importan su configuración de `http://localhost:8888` (`optional:`, así que arrancan igualmente sin él, pero perderían puertos, Kafka y topic). Comprueba que sirve configuración:

```bash
curl localhost:8888/quests/default
```

### 3️⃣ Los servicios (una terminal cada uno)

```bash
./gradlew :adventurers-bootstrap:bootRun
./gradlew :quests-bootstrap:bootRun
./gradlew :treasury-bootstrap:bootRun
./gradlew :gateway:bootRun
```

> 💾 **Persistencia:** por defecto cada servicio usa repositorios **en memoria**. Para usar Postgres (con migraciones Flyway) añade el perfil:
> ```bash
> ./gradlew :quests-bootstrap:bootRun --args='--spring.profiles.active=postgres'
> ```

### 4️⃣ Probar el flujo completo

```bash
./scripts/c13-script.sh     # recorre todo el flujo a través del gateway
```

O a mano, todo por `localhost:8080`:

```bash
# 1. Crear aventurera
curl -s -X POST localhost:8080/api/adventurers \
  -H 'Content-Type: application/json' -d '{"name":"Lucía"}'
# → {"id":"…","name":"Lucía","rank":"NOVICE","experience":0}

# 2. Publicar misión
curl -s -X POST localhost:8080/api/quests \
  -H 'Content-Type: application/json' \
  -d '{"title":"Limpiar la casa","reward":150,"requiredRank":"NOVICE"}'

# 3. Aceptarla  (usa los ids devueltos)
curl -s -X POST localhost:8080/api/quests/$QUEST/accept \
  -H 'Content-Type: application/json' -d '{"adventurerId":"'$LUCIA'"}'

# 4. Completarla → dispara el evento en Kafka
curl -s -X POST localhost:8080/api/quests/$QUEST/complete

# 5. Efectos asíncronos: XP/rango y oro
curl -s localhost:8080/api/adventurers/$LUCIA     # experience: 150, rank: ADEPT
curl -s localhost:8080/api/treasury/accounts/$LUCIA   # balance: 150
```

---

## 🧩 Los servicios en detalle

### 📜 Quests — `:8081`

Dueño del ciclo de vida de la misión: `DRAFT → PUBLISHED → ACCEPTED → COMPLETED`.

| Método | Ruta (vía gateway) | Body | Descripción |
|---|---|---|---|
| `POST` | `/api/quests` | `{title, reward, requiredRank, validForMinutes?}` | Crea y publica una misión (`201`) |
| `GET` | `/api/quests/{id}` | — | Consulta una misión (`404` si no existe) |
| `POST` | `/api/quests/{id}/accept` | `{adventurerId}` | Un aventurero acepta la misión |
| `POST` | `/api/quests/{id}/complete` | — | Completa la misión y **publica evento** |

**Reglas de dominio** (errores como `ProblemDetail`):

| Regla | HTTP |
|---|---|
| Misión o aventurero inexistente | `404` |
| Transición de estado inválida | `409` |
| Rango insuficiente (`NOVICE < ADEPT < VETERAN`) | `422` |
| Máx. **3** misiones activas por aventurero | `422` |
| Misión no publicada / caducada | `422` |
| `adventurers` caído o circuito abierto | `503` |

**Colaboraciones:**
- 🔌 *Sale* → `GET adventurers/{id}` (REST, timeouts de 1 s, **Retry** ×3 con backoff exponencial + **Circuit Breaker** con Resilience4j).
- 📣 *Sale* → evento `QuestCompleted` al topic `guildboard.quest-completed`.
- 🔔 *Sale* → notificación (adaptador de log).

### 🧙 Adventurers — `:8082`

| Método | Ruta (vía gateway) | Body | Descripción |
|---|---|---|---|
| `POST` | `/api/adventurers` | `{name}` (≤ 50) | Registra un aventurero (`NOVICE`, 0 XP) |
| `GET` | `/api/adventurers/{id}` | — | `{id, name, rank, experience}` |

- 📥 *Escucha* `QuestCompleted` → suma XP igual a la recompensa. Los rangos suben con la experiencia: **ADEPT ≥ 100**, **VETERAN ≥ 300** (nunca bajan).
- ☠️ Mensajes que fallan van a la *dead-letter topic* `guildboard.quest-completed-adventurers-dlt`.

### 💰 Treasury — `:8083`

| Método | Ruta (vía gateway) | Descripción |
|---|---|---|
| `GET` | `/api/treasury/accounts/{adventurerId}` | Saldo de la cuenta `{adventurerId, balance}` |
| `GET` | `/api/treasury/accounts/{adventurerId}/ledger-balance` | Saldo **recalculado** sumando el libro contable |

- 📥 *Escucha* `QuestCompleted` → registra una entrada en el *ledger* y acredita la cuenta.
- 🔁 **Idempotente**: pagar dos veces la misma misión se ignora (existe check en servicio + índice único `V2__unique_quest_payment.sql`).
- ☠️ DLT propia: `guildboard.quest-completed-treasury-dlt`.

### 🚪 Gateway — `:8080`

| Ruta externa | Destino | Transformación |
|---|---|---|
| `/api/quests/**` | `:8081` | `StripPrefix=1` → `/quests/**` |
| `/api/adventurers/**` | `:8082` | `StripPrefix=1` → `/adventurers/**` |
| `/api/treasury/**` | `:8083` | `StripPrefix=2` → `/**` (ej. `/accounts/…`) |

### ⚙️ Config Server — `:8888`

Perfil `native`: sirve los ficheros de `config-repo/` (`application.yml` común + uno por servicio).

---

## 🧱 Estructura de cada servicio

Cada servicio son **4 módulos Gradle**, y el compilador impide romper la regla de dependencias:

```
services/<servicio>/
├── domain/        ← lógica pura de negocio (sin frameworks)
├── application/   ← casos de uso + puertos (in / out)
├── adapters/      ← web, JPA, memoria, Kafka, REST client
└── bootstrap/     ← Spring Boot, @Configuration, migraciones, ArchUnit
        dependencias:  bootstrap → adapters → application → domain
```

Tests de arquitectura: `ArchitectureTest` (ArchUnit) por servicio y `StructureTest` en la raíz, que exige los 4 módulos por servicio.

```bash
./gradlew test
```

---

## 🆕 Cambios nuevos y aprendizajes — Capítulo 13

**Tema:** API Gateway. Hasta el capítulo 12 el cliente tenía que conocer tres puertos distintos (`8081`, `8082`, `8083`); ahora hay una única puerta de entrada.

### ✅ Qué se desarrolló

- 🆕 Nuevo módulo `gateway` con **Spring Cloud Gateway (WebMVC)** en el puerto `8080`.
- 🛣️ Tres rutas por prefijo (`/api/quests`, `/api/adventurers`, `/api/treasury`) hacia cada servicio.
- ✂️ Filtros `StripPrefix` para que los servicios **no cambien**: siguen exponiendo `/quests`, `/adventurers`, `/accounts`.
- 🔗 El gateway toma su configuración del Config Server (`config-repo/gateway.yml`).
- 🧪 `scripts/c13-script.sh`: el mismo flujo de siempre, ahora todo contra `:8080`, incluyendo la inyección manual de un evento duplicado en Kafka para comprobar la idempotencia.

### 🧠 Qué se aprendió

| Aprendizaje | Detalle |
|---|---|
| **Un solo punto de entrada** | El cliente ya no depende de la topología interna; los servicios se pueden mover sin que el cliente lo note |
| **`StripPrefix` desacopla** | El prefijo `/api/...` es asunto del gateway, no de los controladores |
| **Ojo con la cuenta de segmentos** | `/api/treasury/accounts/x` necesita `StripPrefix=2` (a diferencia de los otros) porque Treasury no tiene prefijo propio |
| **Gateway ≠ lógica de negocio** | Solo enruta; las reglas siguen en el dominio |
| **Idempotencia = obligatoria con eventos** | Kafka puede entregar dos veces; el `ledger` lo resiste |

---

## 📈 Evolución del aprendizaje

| Cap. | Hito | Aprendizaje clave |
|:---:|---|---|
| **1** | Primer endpoint: caso de uso + adaptador web + repositorio en memoria | Un caso de uso no sabe nada de HTTP |
| **3** | DTOs, mapeadores, errores HTTP, `Clock`, `NotificationSender` | Mapear en el borde; inyectar el tiempo para poder testear |
| **5** | Invariantes de dominio con agregados | El agregado protege sus reglas; estados inválidos imposibles |
| **6** | Mappers y DTOs separados | Dominio ≠ modelo web ≠ modelo de persistencia |
| **7** | Multi-módulo Gradle | Que el **compilador** haga cumplir la arquitectura |
| **8** | Cortes para microservicios (Quests / Adventurers) | Dónde está la frontera de un servicio |
| **10** | Servicios separados, REST + Resilience4j, Postgres + Flyway | Retry, circuit breaker, timeouts y migraciones versionadas |
| **11** | Eventos con Kafka (Treasury, XP) | Consistencia eventual; DLT; consumidores independientes |
| **12** | Config Server | Una sola fuente de configuración para todos |
| **13** | API Gateway | Una única puerta de entrada; `StripPrefix` |

### 🎓 Aprendizajes generales hasta ahora

1. **La arquitectura se puede testear.** ArchUnit y los módulos Gradle convierten las reglas en errores de compilación o de test, no en acuerdos verbales.
2. **El dominio manda.** `Quest` y `Adventurer` no conocen Spring ni JPA. Los errores son un `sealed interface DomainError` con `switch` exhaustivo, traducido a HTTP solo en el adaptador.
3. **Los puertos permiten cambiar la tecnología.** Memoria ↔ Postgres con un perfil, REST ↔ Kafka tras interfaces, sin tocar la lógica.
4. **Una llamada de red falla.** Timeouts, reintentos con backoff y circuit breaker son parte del diseño, y se traducen en un `503` limpio para el cliente.
5. **Los eventos exigen idempotencia y DLT.** Entrega "al menos una vez" implica duplicados; hay que diseñar para ellos.
6. **Cada servicio, su base de datos.** Nada de joins entre servicios: se pide por REST o se reacciona a eventos.
7. **Configuración externalizada.** El mismo binario, distinto entorno.
8. **Una puerta de entrada simplifica al cliente** y oculta la topología.

### 🔭 Siguientes pasos posibles

- Registro/descubrimiento de servicios en vez de URLs fijas.
- Outbox pattern (hoy el guardado y la publicación del evento no son atómicos).
- Seguridad en el gateway (autenticación / rate limiting).
- Docker para los propios servicios y observabilidad (trazas, métricas).
- Tests de integración con Testcontainers.
