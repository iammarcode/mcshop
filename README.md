# mcshop server

### Run mcshop server locally

1.Run all with docker
```bash
docker compose -f docker-compose.local.yml up
```

### Test

1.Start services for testing:
```bash
docker compose -f docker-compose.test.yml up
```
```bash
./mvnw test
```

2.Run test in docker:

```bash
docker build -t mcshop-test --target test .
```

### Run CI locally
```bash
act -n
```
