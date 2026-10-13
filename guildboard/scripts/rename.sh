#!/usr/bin/env bash
set -euo pipefail

ROOT="services"
PACKAGES='com\.guildboard\.(domain|application|adapter|config)'

for S in quests adventurers; do
    echo "==> Renombrando paquetes de $S"

    # Reorganizar directorios Java
    while IFS= read -r -d '' D; do
        mkdir -p "$D/$S"

        for P in domain application adapter config; do
            if [[ -d "$D/$P" ]]; then
                if [[ -e "$D/$S/$P" ]]; then
                    echo "ERROR: Ya existe $D/$S/$P; no se mueve $D/$P" >&2
                    exit 1
                fi

                mv "$D/$P" "$D/$S/$P"
                echo "  Movido: $D/$P -> $D/$S/$P"
            fi
        done
    done < <(
        find "$ROOT/$S" -type d -path '*/src/*/java/com/guildboard' -print0
    )

    # Actualizar package e import en fuentes Java
    while IFS= read -r -d '' F; do
        if grep -qE "$PACKAGES" "$F"; then
            sed -i -E \
                "s/$PACKAGES/com.guildboard.$S.\\1/g" "$F"
            echo "  Actualizado: $F"
        fi
    done < <(find "$ROOT/$S" -type f -name '*.java' -print0)
done

echo
echo "==> Referencias antiguas que aún quedan:"
if grep -rEn \
    --include='*.java' \
    'com\.guildboard\.(domain|application|adapter|config)' \
    "$ROOT"; then
    echo "Revisa las coincidencias anteriores."
else
    echo "No se encontraron referencias a los paquetes antiguos."
fi
