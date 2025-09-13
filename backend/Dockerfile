# Stage 1: Build
FROM gradle:8.5.0-jdk17-alpine AS build
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /workspace

# Gradle Wrapper와 권한
COPY backend/gradlew backend/gradlew
COPY backend/gradle backend/gradle
RUN chmod +x backend/gradlew

# 캐시 최적화를 위한 스크립트 복사
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
COPY backend/build.gradle backend/build.gradle

# 의존성 프리패치(캐시 활용)
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