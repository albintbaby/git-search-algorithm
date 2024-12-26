# Stage 1: Build the project
FROM maven:3.9.4-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean install -e

# Stage 2: Run the project
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/git-search-algorithm.jar git-search-algorithm.jar
CMD ["java", "-jar", "git-search-algorithm.jar"]
EXPOSE 8080
