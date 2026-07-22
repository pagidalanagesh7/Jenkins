# Use an official Java runtime image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy application JAR file
COPY target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]