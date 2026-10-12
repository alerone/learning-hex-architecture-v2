#! /usr/bin/env bash

pkill evince
SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
DOC_DIR="$SCRIPT_DIR/docs/hexagonal-architecture.pdf"
evince "$DOC_DIR" >/dev/null 2>&1 & disown

