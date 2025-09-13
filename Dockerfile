# Stage 1: Build
FROM gradle:8.5.0-jdk17-alpine AS build
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /workspace

# Gradle Wrapper와 권한(backend 기준)
COPY backend/gradlew backend/gradlew
COPY backend/gradle  backend/gradle
RUN chmod +x backend/gradlew

# 빌드 스크립트(캐시 최적화: 루트/모듈 모두 반영)
COPY settings.gradle settings.gradle
COPY build.gradle     build.gradle
COPY backend/build.gradle backend/build.gradle

# 의존성 프리패치(네트워크 일시 오류 시에도 캐시층 형성)
# 필요 시 --no-parallel, --refresh-dependencies 등을 조절하세요.
RUN backend/gradlew --no-daemon :backend:dependencies || true

# 전체 소스 복사
COPY . .

# 모듈 지정 빌드(로그 확장으로 실패 원인 가시화)
RUN backend/gradlew --no-daemon :backend:bootJar -x test --stacktrace --info

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/backend/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]