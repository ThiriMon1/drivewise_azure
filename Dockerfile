FROM amazoncorretto:23
WORKDIR /app
COPY target/drivewise-0.0.1-SNAPSHOT.jar /app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "drivewise-0.0.1-SNAPSHOT.jar"]
