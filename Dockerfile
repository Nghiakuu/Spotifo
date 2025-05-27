FROM openjdk:22
WORKDIR /app
COPY docker-spring-boot-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]