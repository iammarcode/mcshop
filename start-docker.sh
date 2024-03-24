#!/usr/bin/env bash

set -e

chmod -R 777 ./data/localstack/init
chmod -R 777 ./data/mysql/init

docker compose up -d