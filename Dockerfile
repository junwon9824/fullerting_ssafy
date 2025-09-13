# Stage 1: Build
FROM gradle:8.5.0-jdk17-alpine AS build
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /workspace

# Gradle Wrapper�� ����(backend ����)
COPY backend/gradlew backend/gradlew
COPY backend/gradle  backend/gradle
RUN chmod +x backend/gradlew

# ���� ��ũ��Ʈ(ĳ�� ����ȭ: ��Ʈ/��� ��� �ݿ�)
COPY settings.gradle settings.gradle
COPY build.gradle     build.gradle
COPY backend/build.gradle backend/build.gradle

# ������ ������ġ(��Ʈ��ũ �Ͻ� ���� �ÿ��� ĳ���� ����)
# �ʿ� �� --no-parallel, --refresh-dependencies ���� �����ϼ���.
RUN backend/gradlew --no-daemon :backend:dependencies || true

# ��ü �ҽ� ����
COPY . .

# ��� ���� ����(�α� Ȯ������ ���� ���� ����ȭ)
RUN backend/gradlew --no-daemon :backend:bootJar -x test --stacktrace --info

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/backend/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]