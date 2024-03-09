#!/usr/bin/env bash

# deploy
awslocal cloudformation deploy \
  --template-file /etc/localstack/init/ready.d/localstack-infra.yml \
  --stack-name localstack-infra

# delete
# aws --endpoint-url=http://localhost:4566 cloudformation delete-stack --stack-name localstack-infra