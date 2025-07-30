FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/kakao.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java",  "-Dspring.profiles.active=docker","-jar", "app.jar"]