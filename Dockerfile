# Build stage
FROM maven:3.9.7-eclipse-temurin-17 as builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar ./user-subscription-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user-subscription-service.jar"]