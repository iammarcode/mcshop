# mcshop backend

### Run mcshop backend project locally

1.Create required secrets stored in secretsmanager
```bash
mkdir -p ./data/localstack/init/secrets/ &&
cat <<EOF >./data/localstack/init/secrets/smtp-secrets.json
{
  "username": YOUR_SMTP_USERNAME,
  "password": YOUR_SMTP_PASSWORD
}
EOF
```

2.Run with Intellij
```bash
bash start-local.sh
./mvnw spring-boot:run
```

3.Run all with docker
```bash
bash start-docker.sh
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
