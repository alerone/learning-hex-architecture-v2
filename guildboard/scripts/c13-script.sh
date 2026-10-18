#! /usr/bin/env bash

QUESTS=localhost:8080/api/quests
ADVENT=localhost:8080/api/adventurers
TREAS=localhost:8080/api/treasury

K=/opt/kafka/bin
B=localhost:19092
docker compose exec kafka $K/kafka-topics.sh \
    --bootstrap-server $B --list
docker compose exec kafka $K/kafka-consumer-groups.sh \
    --bootstrap-server $B --list

response=$(curl -s -X POST $ADVENT -H "Content-type: application/json" -d '{"name": "Lucía"}')

LUCIA=$(echo "$response" | jq -r '.id')

echo "Adventurer Lucía created:"
echo "$response" | jq

response=$(curl -s -X POST $QUESTS -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}')

echo "Dragon quest created:"
echo "$response"  | jq

DRAGON=$(echo "$response" | jq -r '.id')

response=$(curl -s -X POST $QUESTS -H "Content-type: application/json" -d \
    '{"title": "Limpiar la casa", "reward": 150, "requiredRank": "NOVICE"}')

echo "novice quest created:"
echo "$response" | jq

NOVICE=$(echo "$response" | jq -r '.id')

curl  -X POST $QUESTS/$DRAGON/accept -H "content-type: application/json" \
    -d '{"adventurerId":"'$LUCIA'"}' | jq

curl  -X POST $QUESTS/$NOVICE/accept -H "content-type: application/json" \
    -d '{"adventurerId":"'$LUCIA'"}' | jq

curl  -X POST $QUESTS/$NOVICE/complete | jq

curl $ADVENT/$LUCIA | jq
curl $TREAS/accounts/$LUCIA | jq
echo "LEDGER BALANCE COMPUTED"
curl $TREAS/accounts/$LUCIA/ledger-balance | jq

printf '%s\n' "$NOVICE"'#{"questId":"'"$NOVICE"'","adventurerId":"'"$LUCIA"'","reward":150}' |
    docker compose exec -T kafka "$K/kafka-console-producer.sh" \
        --bootstrap-server "$B" \
        --topic guildboard.quest-completed \
        --property parse.key=true \
        --property key.separator=#

curl $TREAS/accounts/$LUCIA | jq
curl $ADVENT/$LUCIA | jq

