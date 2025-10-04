# Stage 1: Build
FROM openjdk:17-jdk-slim AS builder

# ���� �۾� ���丮 ����
WORKDIR /app/backend

# openjdk:17-jdk-slim�� Java ��ġ ��ο� �°� JAVA_HOME ����
ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$PATH:$JAVA_HOME/bin

# �ҽ� ���� (context root ����, backend ���丮�� code�� ���� ��)
COPY . /app

# gradlew ���� ���� �� ����
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon bootJar -x test

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# ����� JAR ����
COPY --from=builder /app/backend/build/libs/*.jar app.jar

# �� ����
ENTRYPOINT ["java", "-jar", "app.jar"]
