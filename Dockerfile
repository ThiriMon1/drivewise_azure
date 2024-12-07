#FROM amazoncorretto:23
#WORKDIR /app
#COPY target/drivewise-0.0.1-SNAPSHOT.jar /app
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "drivewise-0.0.1-SNAPSHOT.jar"]

# Use an official OpenJDK 21 image as the base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/drivewise-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
