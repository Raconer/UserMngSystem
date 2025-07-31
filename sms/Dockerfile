FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/sms.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java",  "-Dspring.profiles.active=docker", "-jar", "app.jar"]