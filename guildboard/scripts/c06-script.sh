#! /usr/bin/env bash

URL=localhost:8080

response=$(curl -s -X POST $URL/adventurers -H "Content-type: application/json" -d '{"name": "Lucía"}')

LUCIA=$(echo "$response" | jq -r '.id')

echo "Adventurer Lucía created:"
echo "$response" | jq

response=$(curl -s -X POST $URL/quests -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}')

echo "Dragon quest created:"
echo "$response"  | jq

DRAGON=$(echo "$response" | jq -r '.id')

response=$(curl -s -X POST $URL/quests -H "Content-type: application/json" -d \
    '{"title": "Limpiar la casa", "reward": 50, "requiredRank": "NOVICE"}')

echo "novice quest created:"
echo "$response" | jq

NOVICE=$(echo "$response" | jq -r '.id')

curl  -X POST $URL/quests/$DRAGON/accept -H "content-type: application/json" \
    -d '{"adventurerId":"'$LUCIA'"}' | jq

curl  -X POST $URL/quests/$NOVICE/accept -H "content-type: application/json" \
    -d '{"adventurerId":"'$LUCIA'"}' | jq

curl  -X POST $URL/quests/$NOVICE/complete | jq

echo
echo ERRORES con QUEST:

echo TITLE BLANK
curl -s -X POST $URL/quests -H "Content-type: application/json" -d \
    '{"title": "", "reward": 50, "requiredRank": "NOVICE"}' | jq 

echo REWARD 0
curl -s -X POST $URL/quests -H "Content-type: application/json" -d \
    '{"title": "Domar a la princesa", "reward": 0, "requiredRank": "NOVICE"}' | jq


