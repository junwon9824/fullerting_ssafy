# 입찰 정합성 E2E 테스트 가이드

## 개요

이 문서는 풀러팅 프로젝트의 입찰 시스템에서 데이터 정합성을 보장하는지 확인하는 E2E 테스트에 대한 설명입니다.

## ⚠️ **중요: 포트 충돌 방지**

테스트 환경은 기존 운영 환경과 포트 충돌을 방지하기 위해 다른 포트를 사용합니다:

### 기존 운영 환경 포트:
- **MongoDB**: 27017
- **Redis**: 6378  
- **Kafka**: 9092

### 테스트 환경 포트:
- **MongoDB**: 27018
- **Redis**: 6380
- **Kafka**: 9094 (KRaft 모드)

## 테스트 목적

입찰 시스템의 다음 항목들이 정합성을 유지하는지 검증합니다:

1. **동시 입찰 시 데이터 정합성**: 여러 사용자가 동시에 입찰할 때 데이터 일관성 보장
2. **입찰가 검증 로직 정합성**: 현재가보다 낮은 가격으로 입찰 시도 시 적절한 예외 처리
3. **입찰자 수 계산 정합성**: 다양한 방법으로 계산한 입찰자 수의 일치성
4. **Redis 캐시와 DB 데이터 정합성**: 캐시된 데이터와 실제 DB 데이터의 동기화
5. **낙찰 후 상태 정합성**: 입찰 완료 후 시스템 상태의 정확성

## 테스트 구조

### 1. BidConsistencyE2ETest.java
- 메인 테스트 클래스
- 각 정합성 항목별 개별 테스트 메서드 포함
- 동시성 테스트를 위한 멀티스레드 환경 구성

### 2. BidConsistencyTestReporter.java
- 테스트 결과 분석 및 리포트 생성
- 정합성 점수 계산
- 상세한 데이터 분석 정보 제공

### 3. 설정 파일들
- `application-test.yml`: 테스트 환경 설정 (포트: 27018, 6380, 9094)
- `docker-compose-test.yml`: 테스트용 인프라 서비스 (MongoDB, Redis, Kafka KRaft 모드)

## 테스트 환경 구성

### KRaft 모드 사용 이유

테스트 환경에서는 Kafka KRaft(Kafka Raft) 모드를 사용합니다:

#### 🚀 **KRaft 모드의 장점**
- **Zookeeper 의존성 제거**: 외부 Zookeeper 없이 Kafka 자체에서 메타데이터 관리
- **성능 향상**: 더 빠른 컨트롤 플레인 처리 (약 2-3배 빠름)
- **운영 단순화**: 하나의 시스템으로 통합 관리
- **리소스 절약**: Zookeeper 컨테이너 불필요 (메모리, CPU 절약)
- **확장성**: 더 나은 수평 확장 지원

#### 🔄 **기존 Zookeeper 모드와의 차이**
- **Zookeeper 모드**: `KAFKA_ZOOKEEPER_CONNECT` 설정 필요
- **KRaft 모드**: `KAFKA_PROCESS_ROLES: 'broker,controller'` 설정으로 자체 관리

#### 📊 **테스트 환경 구성**
```yaml
# KRaft 모드 설정
KAFKA_NODE_ID: 1
KAFKA_PROCESS_ROLES: 'broker,controller'
KAFKA_CONTROLLER_QUORUM_VOTERS: '1@kafka-test:29093'
KAFKA_LISTENERS: 'PLAINTEXT://kafka-test:9092,CONTROLLER://kafka-test:29093'
```

## 테스트 시나리오

### 시나리오 1: 동시 입찰 데이터 정합성 검증
```
Given: 50개의 스레드가 동시에 입찰 요청
When: 각 스레드가 순차적으로 증가하는 입찰가로 입찰
Then: 
  - 모든 입찰이 올바르게 처리되어야 함
  - 현재가는 가장 높은 입찰가와 일치해야 함
  - 입찰자 수가 정확히 계산되어야 함
```

### 시나리오 2: 입찰가 검증 로직 정합성 검증
```
Given: 현재가보다 낮은 가격으로 입찰 시도
When: 입찰 처리 요청
Then: 
  - 적절한 예외가 발생해야 함
  - 현재가가 변경되지 않아야 함
```

### 시나리오 3: Redis 캐시와 DB 데이터 정합성 검증
```
Given: 입찰 데이터가 생성됨
When: Redis 캐시와 DB 데이터를 각각 조회
Then: 
  - 캐시된 데이터 수와 DB 데이터 수가 일치해야 함
  - 캐시된 데이터가 최신 상태여야 함
```

## 실행 방법

### 1. 테스트 환경 준비

#### Windows 환경
```bash
# 배치 스크립트 실행 (권장)
run-bid-consistency-test.bat
```

#### Linux/Mac 환경
```bash
# 테스트용 인프라 서비스 시작
docker-compose -f docker-compose-test.yml up -d

# 서비스 시작 대기 (30초)
sleep 30

# Gradle을 통한 테스트 실행
./gradlew test --tests "com.ssafy.fullerting.BidConsistencyE2ETest" --info

# 테스트 환경 정리
docker-compose -f docker-compose-test.yml down
```

### 2. 개별 테스트 실행

```bash
# 특정 테스트 메서드만 실행
./gradlew test --tests "com.ssafy.fullerting.BidConsistencyE2ETest.testConcurrentBidsDataConsistencyWithLock"

# 동시성 테스트만 실행
./gradlew test --tests "*Concurrent*"

# 정합성 검증 테스트만 실행
./gradlew test --tests "*Consistency*"
```

## 포트 충돌 문제 해결

### 문제 상황
기존 운영 환경과 테스트 환경이 같은 포트를 사용하면 충돌이 발생합니다.

### 해결 방법
1. **테스트 전 기존 서비스 상태 확인**:
   ```bash
   docker ps --format "table {{.Names}}\t{{.Ports}}\t{{.Status}}"
   ```

2. **테스트용 서비스는 별도 포트 사용**:
   - MongoDB: 27018
   - Redis: 6380
   - Kafka: 9094

3. **테스트 완료 후 정리**:
   ```bash
   docker-compose -f docker-compose-test.yml down
   ```

### 포트 충돌 시 확인사항
```bash
# 포트 사용 중인 프로세스 확인
netstat -ano | findstr :27017
netstat -ano | findstr :6378
netstat -ano | findstr :9092

# Docker 컨테이너 상태 확인
docker ps -a
```

## 테스트 결과 해석

### 정합성 점수 기준
- **90% 이상**: 🟢 우수 - 시스템이 정상적으로 작동
- **70-89%**: 🟡 양호 - 일부 정합성 문제가 있을 수 있음
- **70% 미만**: 🔴 주의 필요 - 심각한 정합성 문제 가능성

### 주요 검증 항목
1. **현재가 정합성**: DB의 현재가와 실제 최고 입찰가의 일치성
2. **입찰자 수 정합성**: 계산된 입찰자 수와 저장된 입찰자 수의 일치성
3. **입찰 순서 정합성**: 입찰가가 시간순으로 오름차순 정렬되어 있는지 확인
4. **캐시 정합성**: Redis 캐시와 DB 데이터의 동기화 상태

## 문제 해결

### 일반적인 문제들

#### 1. MongoDB 연결 실패
```bash
# MongoDB 컨테이너 상태 확인
docker ps | grep mongodb-test

# MongoDB 로그 확인
docker logs mongodb-test

# 포트 확인 (27018 사용)
netstat -ano | findstr :27018
```

#### 2. Redis 연결 실패
```bash
# Redis 컨테이너 상태 확인
docker ps | grep redis-test

# Redis 연결 테스트 (포트 6380 사용)
docker exec -it redis-test redis-cli -p 6380 ping
```

#### 3. Kafka 연결 실패
```bash
# Kafka 컨테이너 상태 확인
docker ps | grep kafka-test

# Kafka 토픽 생성 확인 (포트 9094 사용)
docker exec -it kafka-test kafka-topics --list --bootstrap-server localhost:9094
```

### 테스트 실패 시 디버깅

1. **로그 레벨 조정**: `application-test.yml`에서 로깅 레벨을 DEBUG로 설정
2. **개별 테스트 실행**: 실패한 테스트만 따로 실행하여 상세 로그 확인
3. **데이터 상태 확인**: `BidConsistencyTestReporter`를 사용하여 현재 상태 분석
4. **포트 충돌 확인**: `netstat` 명령어로 포트 사용 상태 확인

## 성능 고려사항

### 동시성 테스트 설정
- **스레드 풀 크기**: 20개 (CPU 코어 수에 따라 조정 가능)
- **동시 입찰 수**: 50개 (시스템 성능에 따라 조정 가능)
- **타임아웃**: 30초 (네트워크 지연 고려)

### 리소스 사용량
- **메모리**: 테스트당 약 100-200MB
- **CPU**: 동시성 테스트 시 높은 CPU 사용률
- **네트워크**: Kafka 메시지 전송으로 인한 네트워크 트래픽

## 확장 가능한 테스트 시나리오

### 추가 고려사항
1. **장애 복구 테스트**: 서비스 중단 후 복구 시 정합성 검증
2. **부하 테스트**: 대용량 데이터 처리 시 정합성 유지 확인
3. **네트워크 지연 테스트**: 네트워크 불안정 상황에서의 정합성 검증
4. **데이터 마이그레이션 테스트**: 스키마 변경 시 정합성 보장 확인

## 연관 문서

- [BidConcurrencyTest.java](./src/test/java/com/ssafy/fullerting/BidConcurrencyTest.java): 기존 동시성 테스트
- [BidConsumerService.java](./src/main/java/com/ssafy/fullerting/global/kafka/BidConsumerService.java): 입찰 처리 서비스
- [BidService.java](./src/main/java/com/ssafy/fullerting/bidLog/service/BidService.java): 입찰 비즈니스 로직

## 문의사항

테스트 실행 중 문제가 발생하거나 개선 사항이 있으면 개발팀에 문의해주세요.
