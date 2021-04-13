FROM gradle:jdk11-openj9 as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace

FROM openjdk:11.0.10-jre-buster
WORKDIR /app
EXPOSE 9011
COPY --from=builder /builder/build/libs/aegro-test-1.0.0.jar .
CMD ["java", "-jar", "aegro-test-1.0.0.jar"]