FROM eclipse-temurin:21-jre
RUN useradd --system --no-create-home guildboard
WORKDIR /app
COPY build/libs/app.jar app.jar
USER guildboard
ENTRYPOINT ["java", "-jar", "app.jar"]
