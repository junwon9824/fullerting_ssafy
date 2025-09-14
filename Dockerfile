# Stage 1: Build
FROM openjdk:17-jdk-slim AS builder

# Set the working directory for subsequent instructions
# This is the new, crucial line
WORKDIR /app/backend

# Copy the Gradle wrapper files and the entire backend directory
COPY . /app
RUN chmod +x gradlew

# Run the build from the correct directory
# The `backend/` part is removed because the WORKDIR is already set
RUN ./gradlew --no-daemon bootJar -x test

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/backend/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
