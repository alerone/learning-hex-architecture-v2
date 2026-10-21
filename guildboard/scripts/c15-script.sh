#!/usr/bin/env bash

tok() {
    curl -s -X POST localhost:9000/token \
    -H 'Content-Type: application/json' \
    -d "{\"username\":\"$1\",\"password\":\"$1\"}" |
    grep -o '"access_token" *: *"[^"]*"' | cut -d'"' -f4
}

QUESTS=localhost:8080/api/quests

MASTER=$(tok master)

curl -s -X POST $QUESTS -H "Authorization: Bearer $MASTER" -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}' -i

Q1=$(curl -s -X POST $QUESTS -H "Authorization: Bearer $MASTER" -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}' | jq -r '.id')

Q2=$(curl -s -X POST $QUESTS -H "Authorization: Bearer $MASTER" -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}' | jq -r '.id')

Q3=$(curl -s -X POST $QUESTS -H "Authorization: Bearer $MASTER" -H "Content-type: application/json" -d \
    '{"title": "Domar al dragón", "reward": 500, "requiredRank": "ADEPT"}' | jq -r '.id')

echo "3 quests creadas. Se pide visualizar una quest una quest:"

curl "$QUESTS/$Q1" -H "Authorization: Bearer $MASTER" | jq 

echo "quest vista. Ahora se va a observar el actuador de métricas"

curl localhost:8081/actuator/metrics/http.server.requests -H "Authorization: Bearer $MASTER" | jq 

curl "localhost:8081/actuator/metrics/http.server.requests?tag=uri:/quests" -H "Authorization: Bearer $MASTER" | jq 
curl "localhost:8081/actuator/metrics/http.server.requests?tag=uri:/quests/\{id\}" -H "Authorization: Bearer $MASTER" | jq 
