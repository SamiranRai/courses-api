# Dockerfile in course-api/
FROM openjdk:22-jdk-slim
VOLUME /tmp
COPY target/courses-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

