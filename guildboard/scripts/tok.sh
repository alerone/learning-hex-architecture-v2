#!/usr/bin/env bash

curl -s -X POST localhost:9000/token \
-H 'Content-Type: application/json' \
-d "{\"username\":\"$1\",\"password\":\"$1\"}" |
grep -o '"access_token" *: *"[^"]*"' | cut -d'"' -f4
