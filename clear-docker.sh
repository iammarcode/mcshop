#!/usr/bin/env bash

docker stop $(docker ps -a -q)
docker rm $(docker ps -aq)

docker volume rm $(docker volume ls -qf dangling=true)