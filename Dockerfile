#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
ADD pom.xml .
COPY /src /src
COPY checkstyle-suppressions.xml /
RUN mvn -f /pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]