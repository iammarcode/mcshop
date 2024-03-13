#!/usr/bin/env bash

set -e

chmod -R 777 ./localstack

docker compose -f docker-compose.local.yml up -d