@echo off
echo ========================================
echo 입찰 정합성 E2E 테스트 실행 스크립트
echo ========================================

echo.
echo 1. 기존 서비스 상태 확인 중...
docker ps --format "table {{.Names}}\t{{.Ports}}\t{{.Status}}"

echo.
echo 2. 테스트 환경 시작 중...
docker-compose -f docker-compose-test.yml up -d

echo.
echo 3. 테스트 서비스 상태 확인 중...
echo MongoDB Test: localhost:27018
echo Redis Test: localhost:6380
echo Kafka Test: localhost:9094

echo.
echo 4. 서비스 시작 대기 중... (30초)
timeout /t 30 /nobreak > nul

echo.
echo 5. 테스트 서비스 연결 상태 확인...
docker ps --filter "name=-test" --format "table {{.Names}}\t{{.Ports}}\t{{.Status}}"

echo.
echo 6. 테스트 실행 중...
echo 테스트 환경: application-test.yml
echo 테스트 클래스: BidConsistencyE2ETest

gradlew test --tests "com.ssafy.fullerting.BidConsistencyE2ETest" --info

echo.
echo 7. 테스트 환경 정리 중...
docker-compose -f docker-compose-test.yml down

echo.
echo ========================================
echo 테스트 완료!
echo ========================================
pause
