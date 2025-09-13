# ? 트러블슈팅

## 1. Redis 캐시 역직렬화 이슈

### 증상

- Redis에서 조회한 데이터를 `BidLogResponse`로 변환 시 `ClassCastException` 발생
- `Integer`를 `BidLogResponse`로 캐스팅하려는 오류 발생

### 원인

- `RedisTemplate`의 기본 직렬화/역직렬화 설정에 따라 데이터가 다르게 저장/조회됨
- `StringRedisTemplate`을 사용하는 경우 `String`으로, `RedisTemplate`을 사용하는 경우 객체로 저장
- 두 가지 경우를 모두 처리하지 않아 발생하는 문제

### 해결 방법

1. Redis에서 조회한 데이터의 타입 확인
2. `LinkedHashMap`인 경우: `objectMapper.convertValue()` 사용
3. `String`(JSON)인 경우: `objectMapper.readValue()`로 역직렬화
4. 그 외 타입은 예외 처리

```java
return redisList.stream()
    .map(obj -> {
        if (obj instanceof LinkedHashMap) {
            return objectMapper.convertValue(obj, BidLogResponse.class);
        } else if (obj instanceof String) {
            try {
                return objectMapper.readValue((String) obj, BidLogResponse.class);
            } catch (Exception e) {
                throw new RuntimeException("Redis 캐시 역직렬화 실패", e);
            }
        } else {
            throw new RuntimeException("알 수 없는 캐시 타입: " + obj.getClass());
        }
    })
    .collect(Collectors.toList());
```

## 2. Kafka Consumer 그룹 이슈

### 증상

- 여러 인스턴스에서 동일한 Kafka 토픽을 구독할 때 메시지가 중복 처리되거나 누락되는 현상 발생

### 원인

- Consumer 그룹 ID가 동일하게 설정되어 있어 발생하는 문제
- 파티션 할당 문제로 인한 불균형한 메시지 처리

### 해결 방법

1. 각 인스턴스마다 고유한 Consumer 그룹 ID 부여
2. 파티션 개수와 컨슈머 개수 조정
3. `auto.offset.reset` 설정을 `latest` 또는 `earliest`로 명시적 지정

```yaml
spring:
  kafka:
    consumer:
      group-id: ${spring.application.name}-${random.uuid}
      auto-offset-reset: latest
```

## 3. JPA N+1 문제

### 증상

- 단일 쿼리 대신 다수의 쿼리가 발생하여 성능 저하
- 특히 `@OneToMany` 관계에서 자주 발생

### 원인

- 지연 로딩으로 인한 N+1 문제
- 컬렉션을 순회할 때마다 추가 쿼리 발생

### 해결 방법

1. `@EntityGraph`를 사용한 패치 조인 적용
2. `@BatchSize`로 배치 사이즈 조정
3. `FetchType.LAZY`로 설정하고 필요한 경우에만 조회

```java
@EntityGraph(attributePaths = {"bids", "seller"})
@Query("SELECT d FROM Deal d WHERE d.id = :id")
Optional<Deal> findByIdWithBids(@Param("id") Long id);
```

## 4. 동시성 제어 문제

### 증상

- 경매 입찰 시 동시에 여러 요청이 들어올 경우 입찰 금액이 덮어써지는 문제 발생

### 원인

- 낙관적/비관적 락이 적용되지 않아 발생하는 동시성 문제

### 해결 방법

1. `@Version`을 사용한 낙관적 락 적용
2. `@Lock(LockModeType.PESSIMISTIC_WRITE)`을 사용한 비관적 락 적용
3. Redis 분산 락 적용

```java
@Transactional
@Lock(LockModeType.PESSIMISTIC_WRITE)
public BidLog placeBid(Long dealId, BidRequest request) {
    // 입찰 처리 로직
}
```

## 5. CORS 이슈

### 증상

- 프론트엔드에서 API 호출 시 CORS 정책 위반 오류 발생

### 원인

- 서버에서 CORS 헤더가 제대로 설정되지 않아 발생

### 해결 방법

1. Spring Security 설정에 CORS 정책 추가
2. 특정 도메인만 허용하도록 설정

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("https://your-frontend-domain.com"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

## 6. JWT 토큰 만료 처리

### 증상

- 액세스 토큰이 만료되었을 때 사용자 경험이 좋지 않은 문제

### 원인

- 토큰 만료 시 로그인 페이지로 리다이렉트되거나, 사용자에게 불편을 주는 방식으로 처리됨

### 해결 방법

1. Axios 인터셉터를 사용해 토큰 만료 시 자동 갱신
2. 리프레시 토큰을 사용한 자동 로그인 유지

```javascript
// Axios 인터셉터 예시
instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const { data } = await refreshToken();
        localStorage.setItem("accessToken", data.accessToken);
        originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;
        return instance(originalRequest);
      } catch (error) {
        // 리프레시 토큰도 만료된 경우 로그아웃 처리
        await logout();
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  },
);
```

??? Redis 인스턴스 중복 실행 및 연결 혼동 트러블슈팅
증상
Spring Boot에서 RedisTemplate으로 조회한 데이터와
Docker 컨테이너에서 redis-cli로 조회한 데이터가 서로 다름

예를 들어, Spring Boot에서는 auction:1:logs 등 특정 키가 보이는데
docker exec -it <redis-container> redis-cli에서 keys \*를 치면 (empty array)로 나옴

redis-cli에서 키를 삭제해도, Spring Boot에서는 여전히 데이터가 조회됨

원인
Windows(로컬)에서 redis-server.exe가 이미 실행 중이었고,

Docker Desktop(WSL2)에서 Redis 컨테이너도 별도로 실행되고 있었음

Spring Boot의 application.yml에는 host: localhost, port: 6379로 설정되어 있었음

이 경우, Spring Boot가 접속하는 localhost:6379가
Windows 로컬 Redis에 먼저 연결될 수 있음
(이미 포트를 점유하고 있기 때문)

Docker Redis도 같은 6379 포트를 사용하지만,
Windows에서 이미 점유 중이면 Docker의 포트포워딩이 제대로 동작하지 않거나
Spring Boot가 로컬 Redis에 우선적으로 연결됨

진단 과정
docker exec -it <redis-container> redis-cli에서 keys \*를 쳤을 때 원하는 키가 안 보임

Spring Boot에서 RedisTemplate으로 keys를 찍으면 데이터가 보임

tasklist | findstr redis 명령으로 Windows에 redis-server.exe가 떠 있는 걸 확인

즉, Spring Boot가 Docker Redis가 아니라 Windows 로컬 Redis에 연결되어 있었음

해결 방법
Windows의 redis-server.exe 프로세스 종료

Windows 명령 프롬프트(cmd) 또는 PowerShell에서 아래 명령 실행:

text
taskkill /F /IM redis-server.exe
성공 메시지 예시:

text
성공: 프로세스 "redis-server.exe"(PID xxxx)이(가) 종료되었습니다.
Docker Redis 컨테이너만 실행 상태로 유지

docker ps에서 0.0.0.0:6379->6379/tcp가 떠 있는지 확인

Spring Boot 재시작

이제 localhost:6379로 접속하면 Docker Redis에만 연결됨

1. Redis List 저장/조회 구조 이해
   저장 시:
   각 입찰 로그 객체(BidLogResponse 등)를
   JSON 문자열로 하나씩 Redis List에 push해야 함.

조회 시:
Redis List의 각 아이템을
단일 객체로 역직렬화해야 함.

2. 자주 발생하는 문제 및 해결법

1) JSON 배열 전체를 한 번에 저장
   문제:
   전체 입찰 로그 리스트를 JSON 배열로 직렬화해
   리스트에 한 번만 push하면,

조회 시 역직렬화 에러(MismatchedInputException: Cannot deserialize value of type ... from Array value)

해결:
반드시 for문 등으로 각 객체를 하나씩 push
(예: for (BidLogResponse resp : responses) { ... })

2. Redis List가 비어 있는데 캐시 데이터가 있다고 나옴
   원인:

LRANGE auction:1:logs -1 0처럼 start > stop이면 항상 빈 배열 반환

실제로는 LRANGE auction:1:logs 0 -1로 전체 조회해야 함

해결:

항상 LRANGE <key> 0 -1로 전체 데이터 확인

애플리케이션에서 Redis에 값이 없으면 DB에서 읽고, 그 결과를 다시 캐싱하는 구조인지 확인
