---
description: Actualiza el README.md con los cambios y aprendizajes del último capítulo
argument-hint: "[nº de capítulo opcional, p. ej. 14]"
allowed-tools: Bash(git log:*), Bash(git diff:*), Bash(git show:*), Bash(git status:*), Bash(git rev-parse:*), Bash(ls:*), Read, Edit, Glob, Grep
---

Actualiza el `README.md` de la raíz del repo para reflejar el **último capítulo completado**. Capítulo indicado por el usuario (si lo hay): `$ARGUMENTS`.

## 1. Averiguar qué cambió

1. Lee el `README.md` actual y localiza el último capítulo documentado (sección `🆕 Cambios nuevos y aprendizajes — Capítulo N` y tabla `📈 Evolución del aprendizaje`).
2. Mira el historial: `git log --oneline -30` y localiza los commits posteriores al último cambio del README (`git log -1 --format=%H -- README.md`). Si el usuario dio un capítulo en `$ARGUMENTS`, usa ese.
3. Revisa el diff de esos commits (`git diff <commit-README>..HEAD --stat` y después el detalle de los ficheros relevantes). Lee también el enunciado/ejercicios del capítulo si existen (`guildboard/docs/ej<N>.md`) y los scripts (`guildboard/scripts/c<N>-script.sh`).
4. Detecta:
   - **Estructura**: módulos nuevos o renombrados en `settings.gradle`, nuevos servicios/puertos, cambios en `config-repo/`, `compose.yaml`, carpetas de `services/`.
   - **Pruebas**: tests nuevos (`*Test.java`, ArchUnit, `StructureTest`), scripts de prueba nuevos y comandos para ejecutarlos.
   - **Puesta en marcha**: pasos, puertos o variables que hayan cambiado.

## 2. Qué actualizar en el README

- **`🆕 Cambios nuevos y aprendizajes — Capítulo N`**: sustituye el contenido por el del nuevo capítulo (esta sección trata solo del último capítulo). Mantén el formato: línea `**Tema:**`, subsección `✅ Qué se desarrolló` (lista con emojis) y `🧠 Qué se aprendió` (tabla Aprendizaje / Detalle).
- **`📈 Evolución del aprendizaje`**: añade una fila a la tabla (`Cap. | Hito | Aprendizaje clave`) para el nuevo capítulo. No borres filas anteriores.
- **`🎓 Aprendizajes generales hasta ahora`**: añade solo los aprendizajes realmente nuevos y transversales (numerados, en negrita la idea + una frase). Reformula alguno existente si el capítulo lo matiza; no dupliques.
- **`🔭 Siguientes pasos posibles`**: quita lo que el capítulo ya resolvió y añade lo que ahora tenga sentido.
- **Solo si hubo cambios**: actualiza `🗺️ Vista general`, `🚀 Puesta en marcha`, `🧩 Los servicios en detalle`, `🧱 Estructura de cada servicio` y la parte de tests (comandos, módulos, puertos, nuevo servicio, nuevos tests de arquitectura o scripts).

## 3. Reglas

- Escribe en español, con el mismo tono, emojis y formato de tablas que el README actual.
- Basa todo en lo que el código y los commits muestran; no inventes funcionalidades ni aprendizajes. Si algo es dudoso, déjalo fuera y menciónalo al final.
- Edita con cambios mínimos (`Edit`); no reescribas secciones que no cambian.
- No hagas commit.
- Termina con un resumen breve: capítulo detectado, secciones modificadas y cualquier duda.
