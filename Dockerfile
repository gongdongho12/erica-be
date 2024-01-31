FROM bellsoft/liberica-openjdk-debian:17 AS builder

ENV WORK /workspace
COPY build.gradle.kts settings.gradle.kts gradlew $WORK/
COPY gradle/ $WORK/gradle
COPY ./src/ $WORK/src

WORKDIR $WORK

RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix gradlew
RUN ./gradlew clean bootJar --parallel --no-daemon

FROM bellsoft/liberica-openjdk-debian:17

MAINTAINER gongdongho12@gmail.com

ARG JAR_FILE=workspace/build/libs/*.jar
COPY --from=builder ${JAR_FILE} app.jar

ENTRYPOINT ["java", \
            "-jar", \
            "-Dspring.profiles.active=prod", \
            "/app.jar" \
]
