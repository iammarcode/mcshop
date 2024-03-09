# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src
ENV AWS_REGION=eu-west-1
ENV AWS_ACCESS_KEY_ID=dummy_key
ENV AWS_SECRET_ACCESS_KEY=dummy_secret

FROM base as test
RUN ["./mvnw", "test"]

FROM base as local
CMD ["sh", "-c", "AWS_REGION=${AWS_REGION} AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} ./mvnw spring-boot:run"]

FROM base as development
#TODO: run in aws ec2
CMD ["./mvnw", "spring-boot:run"]

FROM base as build
RUN ./mvnw package

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
COPY --from=build /app/target/mcshop-*.jar /mcshop-server.jar
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/mcshop-server.jar"]