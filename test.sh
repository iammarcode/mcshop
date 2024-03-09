#!/usr/bin/env bash

set -e

cp .env.test .env

docker compose up -d localstack dbserver redis


#TODO: fix not ready from server
./mvnw test