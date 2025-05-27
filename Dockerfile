FROM openjdk:22
ADD ./docker-spring-boot-app.jar docker-spring-boot-app.jar
ENTRYPOINT ["java", "-jar", "docker-spring-boot-app.jar"]