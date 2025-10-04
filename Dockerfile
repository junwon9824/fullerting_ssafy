# Stage 1: Build
FROM openjdk:17-jdk-slim AS builder

# 빌드 작업 디렉토리 설정
WORKDIR /app/backend

# openjdk:17-jdk-slim의 Java 설치 경로에 맞게 JAVA_HOME 설정
ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$PATH:$JAVA_HOME/bin

# 소스 복사 (context root 기준, backend 디렉토리에 code가 있을 때)
COPY . /app

# gradlew 권한 설정 및 빌드
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon bootJar -x test

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/backend/build/libs/*.jar app.jar

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
