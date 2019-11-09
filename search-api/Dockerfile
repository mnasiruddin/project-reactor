# Docker multi-stage build

# Base Alpine Linux based image with OpenJDK JRE only
FROM gradle:5.6.2-jdk11 as builder

MAINTAINER Mohammed Nasiruddin

WORKDIR /src

COPY . /src

RUN gradle clean build -x test

RUN ls -l /src

FROM openjdk:11-jre-slim

COPY --from=builder /src/build/libs/*-*-SNAPSHOT.jar /app.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=prod -jar /app.jar" ]