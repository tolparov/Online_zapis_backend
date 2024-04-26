#
# Build stage
#
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /
ADD pom.xml .
COPY /src /src
RUN mvn -f /pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /
COPY --from=build target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]