FROM gradle:8.14-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:21-alpine
COPY --from=build /app/build/libs/furima-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=prod --server.port=${PORT:-8080}"]