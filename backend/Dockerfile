# 1단계: Gradle을 이용해 빌드 (Docker Layer Caching 최적화)
FROM gradle:8.5.0-jdk17-alpine AS build
WORKDIR /app

# Gradle 빌드 파일들을 먼저 복사하여 의존성 레이어를 분리합니다.
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 의존성을 먼저 다운로드합니다. 소스 코드가 변경되어도 이 레이어는 캐시됩니다.
# --no-daemon 옵션은 CI 환경에서 권장됩니다.
RUN ./gradlew dependencies --no-daemon

# 소스 코드를 복사합니다.
COPY src ./src

# 애플리케이션을 빌드합니다. (의존성은 이미 다운로드되어 빌드가 빠릅니다)
RUN ./gradlew bootJar --no-daemon -x test

# 2단계: 경량 JRE 이미지에 빌드 결과물을 넣어 실행
#FROM azul/zulu-openjdk:17-jre-alpine
FROM openjdk:17-slim
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일만 복사합니다.
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app/app.jar"]
