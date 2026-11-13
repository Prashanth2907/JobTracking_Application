# Stage 1: Build JAR with Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
# Pre-fetch dependencies
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lightweight Runtime JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create upload directory for resumes
RUN mkdir -p /app/uploads/resumes

COPY --from=builder /app/target/jobtrack-backend-1.0.0.jar app.jar

ENV SPRING_PROFILES_ACTIVE=mysql
ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
