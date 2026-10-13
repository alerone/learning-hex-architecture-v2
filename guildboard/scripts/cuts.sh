#!/usr/bin/env bash
set -euo pipefail

Q="services/quests"
A="services/adventurers"

mv_adv() {
    local src="$Q/$1"
    local dst="$A/$1"

    if [[ ! -f "$src" ]]; then
        echo "ERROR: No existe el archivo origen: $src" >&2
        return 1
    fi

    mkdir -p "$(dirname "$dst")"
    git mv "$src" "$dst"
}

cp_adv() {
    local src="$Q/$1"
    local dst="$A/$1"

    if [[ ! -f "$src" ]]; then
        echo "ERROR: No existe el archivo origen: $src" >&2
        return 1
    fi

    mkdir -p "$(dirname "$dst")"
    cp "$src" "$dst"
}

D="domain/src/main/java/com/guildboard/domain"
P="application/src/main/java/com/guildboard/application"
W="adapters/src/main/java/com/guildboard/adapter/in/web"
O="adapters/src/main/java/com/guildboard/adapter/out"

mv_adv "$D/Adventurer.java"
cp_adv "$D/AdventurerId.java"
cp_adv "$D/Rank.java"

mv_adv "domain/src/test/java/com/guildboard/domain/AdventurerTest.java"


for n in 
    RegisterAdventurerCommand 
    RegisterAdventurerUseCase 
    GetAdventurerUseCase 
    GrantExperienceCommand 
    GrantExperienceUseCase
do
    mv_adv "$P/port/in/$n.java"
done

mv_adv "$P/port/out/AdventurerRepository.java"

for n in 
    RegisterAdventurerService 
    GetAdventurerService 
    GrantExperienceService
do
    mv_adv "$P/service/$n.java"
done

mv_adv "$W/AdventurerController.java"
mv_adv "$W/dto/RegisterAdventurerRequest.java"
mv_adv "$W/dto/AdventurerResponse.java"
mv_adv "$W/mapper/AdventurerWebMapper.java"

mv_adv "$O/memory/InMemoryAdventurerRepository"
