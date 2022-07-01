#!/usr/bin/env bash

PROJECT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../.." &> /dev/null && pwd)
git config core.hooksPath "${PROJECT_DIR}/dev/githooks"
