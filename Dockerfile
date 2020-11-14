FROM openjdk:15-jdk-alpine3.12

VOLUME /tmp

COPY target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
