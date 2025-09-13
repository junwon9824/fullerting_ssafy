# Stage 1: Build the application using Gradle
FROM gradle:8.5.0-jdk17-alpine AS build

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and build files to leverage Docker layer caching
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Download dependencies. This layer is cached if build files don't change.
# --no-daemon is recommended for CI environments.
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source code
COPY src ./src

# Build the application, skipping tests as they are run in the CI stage
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Create the final, smaller image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
