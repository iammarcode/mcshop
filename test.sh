#!/usr/bin/env bash

set -e

cp .env.test .env

docker compose up -d

./mvnw test