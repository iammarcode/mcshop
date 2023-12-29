# Spring Shop Server Application

## Run Shop Server locally

1.You can run it from Maven directly using the Spring Boot Maven plugin. At first you have to kick off a local mysql defined in `docker-compose.yml`.

```bash
docker compose up 
./mvnw spring-boot:run
```

2.Or you can run it all with docker:
```bash
docker compose -f docker-compose.local.yml up
```

# Test Applications
with H2 database
```bash
docker build -t shop-test --target test .
```
or

```bash
./mvnw test
```

## License

The Spring Shop Server application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
