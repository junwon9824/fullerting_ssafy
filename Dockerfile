# Stage 1: Build
FROM gradle:8.5.0-jdk17-alpine AS build
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /workspace

# Gradle Wrapper(backend) + 권한
COPY backend/gradlew backend/gradlew
COPY backend/gradle  backend/gradle
RUN chmod +x backend/gradlew

# backend 빌드 스크립트만 COPY (루트에는 build.gradle/settings.gradle 없음)
COPY backend/build.gradle backend/build.gradle

# 의존성 프리패치(캐시층 확보; 실패해도 계속 진행)
RUN backend/gradlew --no-daemon :backend:dependencies || true

# 전체 소스 복사
COPY . .

# 모듈 지정 빌드
RUN backend/gradlew --no-daemon :backend:bootJar -x test --stacktrace --info

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/backend/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]