FROM openjdk:8-alpine

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/docker-springboot-demo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/share/docker-springboot-demo.jar"]