# 입찰 정합성 E2E 테스트 가이드

## 개요

이 문서는 풀러팅 프로젝트의 입찰 시스템에서 데이터 정합성을 보장하는지 확인하는 E2E 테스트에 대한 설명입니다.

## 테스트 목적

입찰 시스템의 다음 항목들이 정합성을 유지하는지 검증합니다:

1. **동시 입찰 시 데이터 정합성**: 여러 사용자가 동시에 입찰할 때 데이터 일관성 보장
2. **입찰가 검증 로직 정합성**: 현재가보다 낮은 가격으로 입찰 시도 시 적절한 예외 처리
3. **입찰자 수 계산 정합성**: 다양한 방법으로 계산한 입찰자 수의 일치성
4. **Redis 캐시와 DB 데이터 정합성**: 캐시된 데이터와 실제 DB 데이터의 동기화
5. **낙찰 후 상태 정합성**: 입찰 완료 후 시스템 상태의 정확성

## 아키텍처 개선: Kafka 도입 전후 비교

이 프로젝트의 핵심 목표 중 하나는 대규모 동시 입찰 요청에도 안정적이고 빠른 응답성을 제공하는 것입니다. 이를 위해 기존의 동기 처리 방식에서 Apache Kafka를 이용한 비동기 메시지 기반 아키텍처로 시스템을 개선했습니다.

### Kafka 도입 전: 동기 처리 아키텍처

- **데이터 흐름**:

  1.  클라이언트가 입찰 요청을 서버(`@MessageMapping`)로 전송합니다.
  2.  요청을 받은 **웹 스레드**가 즉시 모든 비즈니스 로직을 순차적으로 처리합니다.
      - DB 조회 및 비관적 락(Pessimistic Lock) 획득
      - 입찰 유효성 검증 및 데이터 업데이트
      - DB 락 해제
      - Redis 캐시 업데이트
      - 다른 클라이언트에게 WebSocket으로 결과 브로드캐스팅
  3.  모든 과정이 완료된 후에야 클라이언트에게 응답을 보냅니다.

- **문제점**:
  - **병목 현상**: 다수의 입찰이 동시에 발생하면 DB 락 경합으로 인해 스레드들이 대기 상태에 빠집니다.
  - **느린 응답 속도**: 사용자는 자신의 입찰 요청이 DB 처리까지 모두 끝날 때까지 기다려야 하므로 응답 지연을 직접 체감하게 됩니다.
  - **자원 낭비**: 웹 스레드가 무거운 작업을 처리하느라 장시간 점유되어, 새로운 클라이언트 요청을 받지 못하고 스레드 풀이 고갈될 위험이 있습니다.

### Kafka 도입 후: 비동기 메시지 기반 아키텍처

- **데이터 흐름**:

  1.  클라이언트가 입찰 요청을 서버(`@MessageMapping`)로 전송합니다.
  2.  웹 스레드는 요청 데이터를 Kafka 토픽으로 즉시 전송(**Produce**)한 후, 바로 다음 요청을 처리하기 위해 스레드 풀로 반환됩니다.
  3.  별도의 **컨슈머 스레드**가 Kafka 토픽의 메시지를 가져와(**Consume**) 백그라운드에서 DB 처리, 캐시 업데이트, WebSocket 브로드캐스팅 등 시간 소요가 큰 작업을 수행합니다.

- **개선 효과**:
  - **응답성 향상**: 사용자의 요청을 받는 부분과 실제 처리하는 부분이 분리되어, 사용자는 입찰 요청 후 즉시 '접수 완료'와 같은 빠른 피드백을 받을 수 있습니다.
  - **처리량(Throughput) 증대**: 웹 스레드가 빠르게 반납되므로 훨씬 더 많은 동시 접속 및 입찰 요청을 안정적으로 수용할 수 있습니다.
  - **시스템 안정성 및 확장성 확보**: 특정 작업의 지연이 전체 시스템에 미치는 영향을 최소화하고, 향후 컨슈머 수를 늘리는 것만으로도 처리 성능을 쉽게 확장할 수 있습니다.

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

- `application-test.yml`: 테스트 환경 설정
- `docker-compose-test.yml`: 테스트용 인프라 서비스 (MongoDB, Redis, Kafka)

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

```bash
# 테스트용 인프라 서비스 시작
docker-compose -f docker-compose-test.yml up -d

# 서비스 시작 대기 (30초)
sleep 30
```

### 2. 테스트 실행

#### Windows 환경

```bash
# 배치 스크립트 실행
run-bid-consistency-test.bat
```

#### Linux/Mac 환경

```bash
# Gradle을 통한 테스트 실행
./gradlew test --tests "com.ssafy.fullerting.BidConsistencyE2ETest" --info
```

### 3. 개별 테스트 실행

```bash
# 특정 테스트 메서드만 실행
./gradlew test --tests "com.ssafy.fullerting.BidConsistencyE2ETest.testConcurrentBidsDataConsistencyWithLock"

# 동시성 테스트만 실행
./gradlew test --tests "*Concurrent*"

# 정합성 검증 테스트만 실행
./gradlew test --tests "*Consistency*"
```

## 테스트 결과 해석

### 정합성 점수 기준

- **90% 이상**: ? 우수 - 시스템이 정상적으로 작동
- **70-89%**: ? 양호 - 일부 정합성 문제가 있을 수 있음
- **70% 미만**: ? 주의 필요 - 심각한 정합성 문제 가능성

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
```

#### 2. Redis 연결 실패

```bash
# Redis 컨테이너 상태 확인
docker ps | grep redis-test

# Redis 연결 테스트
docker exec -it redis-test redis-cli ping
```

#### 3. Kafka 연결 실패

```bash
# Kafka 컨테이너 상태 확인
docker ps | grep kafka-test

# Kafka 토픽 생성 확인
docker exec -it kafka-test kafka-topics --list --bootstrap-server localhost:9092
```

### 테스트 실패 시 디버깅

1. **로그 레벨 조정**: `application-test.yml`에서 로깅 레벨을 DEBUG로 설정
2. **개별 테스트 실행**: 실패한 테스트만 따로 실행하여 상세 로그 확인
3. **데이터 상태 확인**: `BidConsistencyTestReporter`를 사용하여 현재 상태 분석

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
