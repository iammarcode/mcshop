#!/bin/bash

# create mail-secrets
awslocal secretsmanager create-secret \
    --name mail-secrets \
    --secret-string file:///etc/localstack/init/ready.d/secrets/mail-secrets.json