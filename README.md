# mcshop server

### Run mcshop server locally

1.Create required secrets stored in secretsmanager
```bash
cat <<EOF >./localstack/secrets/smtp-secrets.json
{
  "username": YOUR_SMTP_USERNAME,
  "password": YOUR_SMTP_PASSWORD
}
EOF
```

2.Run all with docker locally
```bash
bash start-local.sh
```

### Test
Run test locally:
```bash
./mvnw clean test
```

### Run CI/CD locally
```bash
act -n
```
