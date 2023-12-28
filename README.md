# Spring Shop Server Application

## Run Shop Server locally

1.Spring Shop Server is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line (it should work just as well with Java 17 or newer):

```bash
git clone git remote add origin https://github.com/iammarcode/shop.git
cd shop
./mvnw package
java -jar target/*.jar
```

2.Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this, it will pick up changes that you make in the project immediately:

```bash
./mvnw spring-boot:run
```

3.Or you can run it with docker:
```bash
docker compose -f docker-compose.local.yml up --build
```

## Database configuration

In its default configuration, Shop Server uses an in-memory database (H2) which
gets populated at startup with data. The h2 console is exposed at `http://localhost:8080/h2-console`,
and it is possible to inspect the content of the database using the `jdbc:h2:mem:<uuid>` URL. The UUID is printed at startup to the console.

A similar setup is provided for MySQL and PostgreSQL if a persistent database configuration is needed. Note that whenever the database type changes, the app needs to run with a different profile: `spring.profiles.active=mysql` for MySQL or `spring.profiles.active=postgres` for PostgreSQL.

You can start MySQL or PostgreSQL locally with whatever installer works for your OS or use docker:

```bash
docker run -e MYSQL_USER=shopuser -e MYSQL_PASSWORD=shoppassword -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=shop -p 3306:3306 mysql:8.2
```

or

```bash
docker run -e POSTGRES_USER=shopuser -e POSTGRES_PASSWORD=shoppassword -e POSTGRES_DB=shop -p 5432:5432 postgres:16.1
```

Instead of vanilla `docker` you can also use the provided `docker-compose.yml` file to start the database containers. Each one has a profile just like the Spring profile:

```bash
docker-compose --profile mysql up
```

or

```bash
docker-compose --profile postgres up
```

## Test Applications
```bash
docker build -t shop-test --target test .
```

## Working with Shop Server in your IDE

### Prerequisites

The following items should be installed in your system:

- Java 17 or newer (full JDK, not a JRE)
- [Git command line tool](https://help.github.com/articles/set-up-git)
- Your preferred IDE
    - Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
      not there, follow the install process [here](https://www.eclipse.org/m2e/)
    - [Spring Tools Suite](https://spring.io/tools) (STS)
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - [VS Code](https://code.visualstudio.com)

## License

The Spring Shop Server application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
