#!/usr/bin/env bash
set -euo pipefail

Q="services/quests"
A="services/adventurers"

move_file() {
    local rel="$1"
    local src="$Q/$rel"
    local dst="$A/$rel"

    if [[ -f "$dst" ]]; then
        echo "YA EXISTE EN DESTINO: $dst"
        return 0
    fi

    if [[ ! -f "$src" ]]; then
        echo "NO ENCONTRADO (se omite): $src"
        return 0
    fi

    mkdir -p "$(dirname "$dst")"
    git mv "$src" "$dst"
    echo "MOVIDO: $rel"
}

# Dominio
move_file "domain/src/main/java/com/guildboard/domain/AdventurerId.java"
move_file "domain/src/main/java/com/guildboard/domain/Rank.java"

# Aplicación
for name in \
    RegisterAdventurerCommand \
    RegisterAdventurerUseCase \
    GetAdventurerUseCase \
    GrantExperienceCommand \
    GrantExperienceUseCase
do
    move_file "application/src/main/java/com/guildboard/application/port/in/$name.java"
done

move_file "application/src/main/java/com/guildboard/application/port/out/AdventurerRepository.java"

for name in \
    RegisterAdventurerService \
    GetAdventurerService \
    GrantExperienceService
do
    move_file "application/src/main/java/com/guildboard/application/service/$name.java"
done

# Adaptadores web
move_file "adapters/src/main/java/com/guildboard/adapter/in/web/AdventurerController.java"
move_file "adapters/src/main/java/com/guildboard/adapter/in/web/dto/RegisterAdventurerRequest.java"
move_file "adapters/src/main/java/com/guildboard/adapter/in/web/dto/AdventurerResponse.java"
move_file "adapters/src/main/java/com/guildboard/adapter/in/web/mapper/AdventurerWebMapper.java"

# Adaptadores de persistencia en memoria
move_file "adapters/src/main/java/com/guildboard/adapter/out/memory/InMemoryAdventurerRepository.java"

echo
echo "Migración de archivos seleccionados terminada."

