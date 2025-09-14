# Stage 1: Build
FROM openjdk:17-jdk-slim AS builder

# Set the working directory for subsequent instructions
WORKDIR /app/backend

# Explicitly set JAVA_HOME inside the Docker container
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# Copy the entire backend directory
COPY . /app

# The `backend/` part is no longer needed since we are already inside the `backend` directory
RUN chmod +x ./gradlew

# Run the build from the correct directory
RUN ./gradlew --no-daemon bootJar -x test

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

# Set the correct working directory for the runtime
WORKDIR /app
COPY --from=builder /app/backend/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
