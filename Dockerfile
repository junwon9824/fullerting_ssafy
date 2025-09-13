# Stage 1: Build
FROM gradle:8.5.0-jdk17-alpine AS build
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /workspace

# Gradle Wrapper(backend) + ����
COPY backend/gradlew backend/gradlew
COPY backend/gradle  backend/gradle
RUN chmod +x backend/gradlew

# backend ���� ��ũ��Ʈ�� COPY (��Ʈ���� build.gradle/settings.gradle ����)
COPY backend/build.gradle backend/build.gradle

# ������ ������ġ(ĳ���� Ȯ��; �����ص� ��� ����)
RUN backend/gradlew --no-daemon :backend:dependencies || true

# ��ü �ҽ� ����
COPY . .

# ��� ���� ����
RUN backend/gradlew --no-daemon :backend:bootJar -x test --stacktrace --info

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/backend/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]