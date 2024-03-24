# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /mcshop
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY mcshop-entry/pom.xml ./mcshop-entry/
COPY mcshop-model/pom.xml ./mcshop-model/
RUN ./mvnw dependency:resolve
COPY mcshop-entry/src ./mcshop-entry/src/
COPY mcshop-model/src ./mcshop-model/src/
ENV AWS_REGION=ap-southeast-1
ENV AWS_ACCESS_KEY_ID=dummy_key
ENV AWS_SECRET_ACCESS_KEY=dummy_secret

FROM base as test
RUN ["./mvnw", "test"]

FROM base as local
CMD ["sh", "-c", "AWS_REGION=${AWS_REGION} AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} ./mvnw -f ./mcshop-entry/pom.xml spring-boot:run -Dspring-boot.run.profiles=docker"]

FROM base as development
CMD ["./mvnw", "spring-boot:run"]

FROM base as build
RUN ./mvnw package

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
COPY --from=build /app/target/mcshop-*.jar /mcshop-entry.jar
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/mcshop-entry.jar"]