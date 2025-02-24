FROM openjdk:17
MAINTAINER Jiwon Park <5upportpark7@gmail.com>

ARG JAR_FILE_NAME=build/libs/retry_view.jar

COPY build/libs/retry_view.jar app.jar

ENV USER_PROFILE dev

EXPOSE 8080

# redis-server /etc/redis.conf
CMD ["java", "-Dspring.profiles.active=${USER_PROFILE}", "-jar", "/app.jar"]