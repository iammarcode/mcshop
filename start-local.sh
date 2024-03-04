#!/usr/bin/env bash

set -e

cp .env.local .env

docker compose up -d