#!/usr/bin/env bash

set -e

cp .env.docker .env

docker compose up -d