#! /usr/bin/env bash

QUESTS=localhost:8081
ADVENT=localhost:8082
TREAS=localhost:8083

response=$(curl -s -X POST $ADVENT/adventurers -H "Content-type: application/json" -d '{"name": "Lucía"}')

LUCIA=$(echo "$response" | jq -r '.id')

echo "Adventurer Lucía created:"
echo "$response" | jq

response=$(curl -s -X POST $QUESTS/quests -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}')

echo "Dragon quest created:"
echo "$response"  | jq

DRAGON=$(echo "$response" | jq -r '.id')

response=$(curl -s -X POST $QUESTS/quests -H "Content-type: application/json" -d \
    '{"title": "Limpiar la casa", "reward": 150, "requiredRank": "NOVICE"}')

echo "novice quest created:"
echo "$response" | jq

NOVICE=$(echo "$response" | jq -r '.id')

curl  -X POST $QUESTS/quests/$DRAGON/accept -H "content-type: application/json" \
    -d '{"adventurerId":"'$LUCIA'"}' | jq

curl  -X POST $QUESTS/quests/$NOVICE/accept -H "content-type: application/json" \
    -d '{"adventurerId":"'$LUCIA'"}' | jq

curl  -X POST $QUESTS/quests/$NOVICE/complete | jq

curl -X POST $TREAS/payments -H "Content-Type: application/json" \
    -d '{"questId": "'$NOVICE'", "adventurerId": "'$LUCIA'", "amount": 150}' | jq

curl $ADVENT/adventurers/$LUCIA | jq

curl $TREAS/accounts/$LUCIA | jq
echo "LEDGER BALANCE COMPUTED"
curl $TREAS/accounts/$LUCIA/ledger-balance | jq

curl -X POST $TREAS/payments -H "Content-Type: application/json" \
    -d '{"questId": "'$NOVICE'", "adventurerId": "'$LUCIA'", "amount": 150}' 

echo
echo ERRORES con QUEST:

echo TITLE BLANK
curl -s -X POST $URL/quests -H "Content-type: application/json" -d \
    '{"title": "", "reward": 50, "requiredRank": "NOVICE"}' | jq 

echo REWARD 0
curl -s -X POST $URL/quests -H "Content-type: application/json" -d \
    '{"title": "Domar a la princesa", "reward": 0, "requiredRank": "NOVICE"}' | jq


