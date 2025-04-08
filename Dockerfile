FROM azul/zulu-openjdk:21-jre
LABEL authors="rainmaker"

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]