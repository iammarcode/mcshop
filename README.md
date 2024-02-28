# Spring Shop Server Application

## Run Shop Server locally

1.Run it from Maven directly using the Spring Boot Maven plugin.

```bash
docker compose -f docker-compose.test.yml up
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=test"
```

2.Or run it all with docker
```bash
docker compose -f docker-compose.local.yml up
```

# Test Applications

1.Start databases services:
```bash
docker compose -f docker-compose.test.yml up
```
2.Run test
```bash
./mvnw test
```

or 

```bash
docker build -t shop-test --target test .
```
