# ? Ʈ������

## 1. Redis ĳ�� ������ȭ �̽�

### ����

- Redis���� ��ȸ�� �����͸� `BidLogResponse`�� ��ȯ �� `ClassCastException` �߻�
- `Integer`�� `BidLogResponse`�� ĳ�����Ϸ��� ���� �߻�

### ����

- `RedisTemplate`�� �⺻ ����ȭ/������ȭ ������ ���� �����Ͱ� �ٸ��� ����/��ȸ��
- `StringRedisTemplate`�� ����ϴ� ��� `String`����, `RedisTemplate`�� ����ϴ� ��� ��ü�� ����
- �� ���� ��츦 ��� ó������ �ʾ� �߻��ϴ� ����

### �ذ� ���

1. Redis���� ��ȸ�� �������� Ÿ�� Ȯ��
2. `LinkedHashMap`�� ���: `objectMapper.convertValue()` ���
3. `String`(JSON)�� ���: `objectMapper.readValue()`�� ������ȭ
4. �� �� Ÿ���� ���� ó��

```java
return redisList.stream()
    .map(obj -> {
        if (obj instanceof LinkedHashMap) {
            return objectMapper.convertValue(obj, BidLogResponse.class);
        } else if (obj instanceof String) {
            try {
                return objectMapper.readValue((String) obj, BidLogResponse.class);
            } catch (Exception e) {
                throw new RuntimeException("Redis ĳ�� ������ȭ ����", e);
            }
        } else {
            throw new RuntimeException("�� �� ���� ĳ�� Ÿ��: " + obj.getClass());
        }
    })
    .collect(Collectors.toList());
```

## 2. Kafka Consumer �׷� �̽�

### ����

- ���� �ν��Ͻ����� ������ Kafka ������ ������ �� �޽����� �ߺ� ó���ǰų� �����Ǵ� ���� �߻�

### ����

- Consumer �׷� ID�� �����ϰ� �����Ǿ� �־� �߻��ϴ� ����
- ��Ƽ�� �Ҵ� ������ ���� �ұ����� �޽��� ó��

### �ذ� ���

1. �� �ν��Ͻ����� ������ Consumer �׷� ID �ο�
2. ��Ƽ�� ������ ������ ���� ����
3. `auto.offset.reset` ������ `latest` �Ǵ� `earliest`�� ����� ����

```yaml
spring:
  kafka:
    consumer:
      group-id: ${spring.application.name}-${random.uuid}
      auto-offset-reset: latest
```

## 3. JPA N+1 ����

### ����

- ���� ���� ��� �ټ��� ������ �߻��Ͽ� ���� ����
- Ư�� `@OneToMany` ���迡�� ���� �߻�

### ����

- ���� �ε����� ���� N+1 ����
- �÷����� ��ȸ�� ������ �߰� ���� �߻�

### �ذ� ���

1. `@EntityGraph`�� ����� ��ġ ���� ����
2. `@BatchSize`�� ��ġ ������ ����
3. `FetchType.LAZY`�� �����ϰ� �ʿ��� ��쿡�� ��ȸ

```java
@EntityGraph(attributePaths = {"bids", "seller"})
@Query("SELECT d FROM Deal d WHERE d.id = :id")
Optional<Deal> findByIdWithBids(@Param("id") Long id);
```

## 4. ���ü� ���� ����

### ����

- ��� ���� �� ���ÿ� ���� ��û�� ���� ��� ���� �ݾ��� ��������� ���� �߻�

### ����

- ������/����� ���� ������� �ʾ� �߻��ϴ� ���ü� ����

### �ذ� ���

1. `@Version`�� ����� ������ �� ����
2. `@Lock(LockModeType.PESSIMISTIC_WRITE)`�� ����� ����� �� ����
3. Redis �л� �� ����

```java
@Transactional
@Lock(LockModeType.PESSIMISTIC_WRITE)
public BidLog placeBid(Long dealId, BidRequest request) {
    // ���� ó�� ����
}
```

## 5. CORS �̽�

### ����

- ����Ʈ���忡�� API ȣ�� �� CORS ��å ���� ���� �߻�

### ����

- �������� CORS ����� ����� �������� �ʾ� �߻�

### �ذ� ���

1. Spring Security ������ CORS ��å �߰�
2. Ư�� �����θ� ����ϵ��� ����

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

## 6. JWT ��ū ���� ó��

### ����

- �׼��� ��ū�� ����Ǿ��� �� ����� ������ ���� ���� ����

### ����

- ��ū ���� �� �α��� �������� �����̷�Ʈ�ǰų�, ����ڿ��� ������ �ִ� ������� ó����

### �ذ� ���

1. Axios ���ͼ��͸� ����� ��ū ���� �� �ڵ� ����
2. �������� ��ū�� ����� �ڵ� �α��� ����

```javascript
// Axios ���ͼ��� ����
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
        // �������� ��ū�� ����� ��� �α׾ƿ� ó��
        await logout();
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  },
);
```

??? Redis �ν��Ͻ� �ߺ� ���� �� ���� ȥ�� Ʈ������
����
Spring Boot���� RedisTemplate���� ��ȸ�� �����Ϳ�
Docker �����̳ʿ��� redis-cli�� ��ȸ�� �����Ͱ� ���� �ٸ�

���� ���, Spring Boot������ auction:1:logs �� Ư�� Ű�� ���̴µ�
docker exec -it <redis-container> redis-cli���� keys \*�� ġ�� (empty array)�� ����

redis-cli���� Ű�� �����ص�, Spring Boot������ ������ �����Ͱ� ��ȸ��

����
Windows(����)���� redis-server.exe�� �̹� ���� ���̾���,

Docker Desktop(WSL2)���� Redis �����̳ʵ� ������ ����ǰ� �־���

Spring Boot�� application.yml���� host: localhost, port: 6379�� �����Ǿ� �־���

�� ���, Spring Boot�� �����ϴ� localhost:6379��
Windows ���� Redis�� ���� ����� �� ����
(�̹� ��Ʈ�� �����ϰ� �ֱ� ����)

Docker Redis�� ���� 6379 ��Ʈ�� ���������,
Windows���� �̹� ���� ���̸� Docker�� ��Ʈ�������� ����� �������� �ʰų�
Spring Boot�� ���� Redis�� �켱������ �����

���� ����
docker exec -it <redis-container> redis-cli���� keys \*�� ���� �� ���ϴ� Ű�� �� ����

Spring Boot���� RedisTemplate���� keys�� ������ �����Ͱ� ����

tasklist | findstr redis ������� Windows�� redis-server.exe�� �� �ִ� �� Ȯ��

��, Spring Boot�� Docker Redis�� �ƴ϶� Windows ���� Redis�� ����Ǿ� �־���

�ذ� ���
Windows�� redis-server.exe ���μ��� ����

Windows ��� ������Ʈ(cmd) �Ǵ� PowerShell���� �Ʒ� ��� ����:

text
taskkill /F /IM redis-server.exe
���� �޽��� ����:

text
����: ���μ��� "redis-server.exe"(PID xxxx)��(��) ����Ǿ����ϴ�.
Docker Redis �����̳ʸ� ���� ���·� ����

docker ps���� 0.0.0.0:6379->6379/tcp�� �� �ִ��� Ȯ��

Spring Boot �����

���� localhost:6379�� �����ϸ� Docker Redis���� �����

1. Redis List ����/��ȸ ���� ����
   ���� ��:
   �� ���� �α� ��ü(BidLogResponse ��)��
   JSON ���ڿ��� �ϳ��� Redis List�� push�ؾ� ��.

��ȸ ��:
Redis List�� �� ��������
���� ��ü�� ������ȭ�ؾ� ��.

2. ���� �߻��ϴ� ���� �� �ذ��

1) JSON �迭 ��ü�� �� ���� ����
   ����:
   ��ü ���� �α� ����Ʈ�� JSON �迭�� ����ȭ��
   ����Ʈ�� �� ���� push�ϸ�,

��ȸ �� ������ȭ ����(MismatchedInputException: Cannot deserialize value of type ... from Array value)

�ذ�:
�ݵ�� for�� ������ �� ��ü�� �ϳ��� push
(��: for (BidLogResponse resp : responses) { ... })

2. Redis List�� ��� �ִµ� ĳ�� �����Ͱ� �ִٰ� ����
   ����:

LRANGE auction:1:logs -1 0ó�� start > stop�̸� �׻� �� �迭 ��ȯ

�����δ� LRANGE auction:1:logs 0 -1�� ��ü ��ȸ�ؾ� ��

�ذ�:

�׻� LRANGE <key> 0 -1�� ��ü ������ Ȯ��

���ø����̼ǿ��� Redis�� ���� ������ DB���� �а�, �� ����� �ٽ� ĳ���ϴ� �������� Ȯ��
