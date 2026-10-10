#! /usr/bin/env bash

pkill evince
evince /home/aler/learning/v2-hex-arc/docs/hexagonal-architecture.pdf >/dev/null 2>&1 & disown

