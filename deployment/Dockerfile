FROM eclipse-temurin:21-jdk-ubi10-minimal AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :web:bootJar

FROM eclipse-temurin:21-jre-ubi10-minimal
WORKDIR /app
COPY --from=builder /app/web/build/libs/web-1.0.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]