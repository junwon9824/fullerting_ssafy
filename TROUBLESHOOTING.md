# �윓� �듃�윭釉붿뒋�똿

## 1. Redis 罹먯떆 �뿭吏곷젹�솕 �씠�뒋

### 利앹긽

- Redis�뿉�꽌 議고쉶�븳 �뜲�씠�꽣瑜 � `BidLogResponse`濡 � 蹂 ��솚 �떆 `ClassCastException` 諛쒖깮
- `Integer`瑜 � `BidLogResponse`濡 � 罹먯뒪�똿�븯�젮�뒗 �삤瑜 � 諛쒖깮

### �썝�씤

- `RedisTemplate`�쓽 湲곕낯 吏곷젹�솕/�뿭吏곷젹�솕 �꽕�젙�뿉 �뵲�씪 �뜲�씠�꽣媛 � �떎瑜닿쾶 ����옣/議고쉶�맖
- `StringRedisTemplate`�쓣 �궗�슜�븯�뒗 寃쎌슦 `String`�쑝濡 �, `RedisTemplate`�쓣 �궗�슜�븯�뒗 寃쎌슦 媛앹껜濡 � ����옣
- �몢 媛 � 吏 � 寃쎌슦瑜 � 紐 ⑤몢 泥섎━�븯吏 � �븡�븘 諛쒖깮�븯�뒗 臾몄젣

### �빐寃 � 諛 ⑸쾿

1. Redis�뿉�꽌 議고쉶�븳 �뜲�씠�꽣�쓽 ����엯 �솗�씤
2. `LinkedHashMap`�씤 寃쎌슦: `objectMapper.convertValue()` �궗�슜
3. `String`(JSON)�씤 寃쎌슦: `objectMapper.readValue()`濡 � �뿭吏곷젹�솕
4. 洹 � �쇅 ����엯��� �삁�쇅 泥섎━

```java
return redisList.stream()
    .map(obj -> {
        if (obj instanceof LinkedHashMap) {
            return objectMapper.convertValue(obj, BidLogResponse.class);
        } else if (obj instanceof String) {
            try {
                return objectMapper.readValue((String) obj, BidLogResponse.class);
            } catch (Exception e) {
                throw new RuntimeException("Redis 罹먯떆 �뿭吏곷젹�솕 �떎�뙣", e);
            }
        } else {
            throw new RuntimeException("�븣 �닔 �뾾�뒗 罹먯떆 ����엯: " + obj.getClass());
        }
    })
    .collect(Collectors.toList());
```

## 2. Kafka Consumer 洹몃９ �씠�뒋

### 利앹긽

- �뿬�윭 �씤�뒪�꽩�뒪�뿉�꽌 �룞�씪�븳 Kafka �넗�뵿�쓣 援 щ룆�븷 �븣 硫붿떆吏 � 媛 � 以묐났 泥섎━�릺嫄곕굹 �늻�씫�릺�뒗 �쁽�긽 諛쒖깮

### �썝�씤

- Consumer 洹몃９ ID 媛 � �룞�씪�븯寃 � �꽕�젙�릺�뼱 �엳�뼱 諛쒖깮�븯�뒗 臾몄젣
- �뙆�떚�뀡 �븷�떦 臾몄젣濡 � �씤�븳 遺덇퇏�삎�븳 硫붿떆吏 � 泥섎━

### �빐寃 � 諛 ⑸쾿

1. 媛 � �씤�뒪�꽩�뒪留덈떎 怨좎쑀�븳 Consumer 洹몃９ ID 遺 ��뿬
2. �뙆�떚�뀡 媛쒖닔��� 而 ⑥뒋癒 � 媛쒖닔 議곗젙
3. `auto.offset.reset` �꽕�젙�쓣 `latest` �삉�뒗 `earliest`濡 � 紐낆떆�쟻 吏 ��젙

```yaml
spring:
  kafka:
    consumer:
      group-id: ${spring.application.name}-${random.uuid}
      auto-offset-reset: latest
```

## 3. JPA N+1 臾몄젣

### 利앹긽

- �떒�씪 荑쇰━ ����떊 �떎�닔�쓽 荑쇰━ 媛 � 諛쒖깮�븯�뿬 �꽦�뒫 ����븯
- �듅�엳 `@OneToMany` 愿 � 怨꾩뿉�꽌 �옄二 � 諛쒖깮

### �썝�씤

- 吏 ��뿰 濡쒕뵫�쑝濡 � �씤�븳 N+1 臾몄젣
- 而 щ젆�뀡�쓣 �닚�쉶�븷 �븣留덈떎 異붽�� 荑쇰━ 諛쒖깮

### �빐寃 � 諛 ⑸쾿

1. `@EntityGraph`瑜 � �궗�슜�븳 �뙣移 � 議곗씤 �쟻�슜
2. `@BatchSize`濡 � 諛곗튂 �궗�씠利 � 議곗젙
3. `FetchType.LAZY`濡 � �꽕�젙�븯怨 � �븘�슂�븳 寃쎌슦�뿉留 � 議고쉶

```java
@EntityGraph(attributePaths = {"bids", "seller"})
@Query("SELECT d FROM Deal d WHERE d.id = :id")
Optional<Deal> findByIdWithBids(@Param("id") Long id);
```

## 4. �룞�떆�꽦 �젣�뼱 臾몄젣

### 利앹긽

- 寃쎈ℓ �엯李 � �떆 �룞�떆�뿉 �뿬�윭 �슂泥 ��씠 �뱾�뼱�삱 寃쎌슦 �엯李 � 湲덉븸�씠 �뜮�뼱�뜥吏 ��뒗 臾몄젣 諛쒖깮

### �썝�씤

- �굺愿 ��쟻/鍮꾧���쟻 �씫�씠 �쟻�슜�릺吏 � �븡�븘 諛쒖깮�븯�뒗 �룞�떆�꽦 臾몄젣

### �빐寃 � 諛 ⑸쾿

1. `@Version`�쓣 �궗�슜�븳 �굺愿 ��쟻 �씫 �쟻�슜
2. `@Lock(LockModeType.PESSIMISTIC_WRITE)`�쓣 �궗�슜�븳 鍮꾧���쟻 �씫 �쟻�슜
3. Redis 遺꾩궛 �씫 �쟻�슜

```java
@Transactional
@Lock(LockModeType.PESSIMISTIC_WRITE)
public BidLog placeBid(Long dealId, BidRequest request) {
    // �엯李� 泥섎━ 濡쒖쭅
}
```

## 5. CORS �씠�뒋

### 利앹긽

- �봽濡좏듃�뿏�뱶�뿉�꽌 API �샇異 � �떆 CORS �젙梨 � �쐞諛 � �삤瑜 � 諛쒖깮

### �썝�씤

- �꽌踰꾩뿉�꽌 CORS �뿤�뜑媛 � �젣��� 濡 � �꽕�젙�릺吏 � �븡�븘 諛쒖깮

### �빐寃 � 諛 ⑸쾿

1. Spring Security �꽕�젙�뿉 CORS �젙梨 � 異붽��
2. �듅�젙 �룄硫붿씤留 � �뿀�슜�븯�룄濡 � �꽕�젙

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

## 6. JWT �넗�겙 留뚮즺 泥섎━

### 利앹긽

- �븸�꽭�뒪 �넗�겙�씠 留뚮즺�릺�뿀�쓣 �븣 �궗�슜�옄 寃쏀뿕�씠 醫뗭�� �븡��� 臾몄젣

### �썝�씤

- �넗�겙 留뚮즺 �떆 濡쒓렇�씤 �럹�씠吏 � 濡 � 由 щ떎�씠�젆�듃�릺嫄곕굹, �궗�슜�옄�뿉寃 � 遺덊렪�쓣 二쇰뒗 諛 ⑹떇�쑝濡 � 泥섎━�맖

### �빐寃 � 諛 ⑸쾿

1. Axios �씤�꽣�뀎�꽣瑜 � �궗�슜�빐 �넗�겙 留뚮즺 �떆 �옄�룞 媛깆떊
2. 由 ы봽�젅�떆 �넗�겙�쓣 �궗�슜�븳 �옄�룞 濡쒓렇�씤 �쑀吏 �

```javascript
// Axios �씤�꽣�뀎�꽣 �삁�떆
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
        // 由ы봽�젅�떆 �넗�겙�룄 留뚮즺�맂 寃쎌슦 濡쒓렇�븘�썐 泥섎━
        await logout();
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  },
);
```

者 � Redis �씤�뒪�꽩�뒪 以묐났 �떎�뻾 諛 � �뿰寃 � �샎�룞 �듃�윭釉붿뒋�똿
利앹긽
Spring Boot�뿉�꽌 RedisTemplate�쑝濡 � 議고쉶�븳 �뜲�씠�꽣���
Docker 而 ⑦뀒�씠�꼫�뿉�꽌 redis-cli 濡 � 議고쉶�븳 �뜲�씠�꽣媛 � �꽌濡 � �떎由 �

�삁瑜 � �뱾�뼱, Spring Boot�뿉�꽌�뒗 auction:1:logs �벑 �듅�젙 �궎媛 � 蹂댁씠�뒗�뜲
docker exec -it <redis-container> redis-cli�뿉�꽌 keys \*瑜 � 移섎㈃ (empty array)濡 � �굹�샂

redis-cli�뿉�꽌 �궎瑜 � �궘�젣�빐�룄, Spring Boot�뿉�꽌�뒗 �뿬�쟾�엳 �뜲�씠�꽣媛 � 議고쉶�맖

�썝�씤
Windows(濡쒖뺄)�뿉�꽌 redis-server.exe 媛 � �씠誘 � �떎�뻾 以묒씠�뿀怨 �,

Docker Desktop(WSL2)�뿉�꽌 Redis 而 ⑦뀒�씠�꼫�룄 蹂꾨룄濡 � �떎�뻾�릺怨 � �엳�뿀�쓬

Spring Boot�쓽 application.yml�뿉�뒗 host: localhost, port: 6379 濡 � �꽕�젙�릺�뼱 �엳�뿀�쓬

�씠 寃쎌슦, Spring Boot 媛 � �젒�냽�븯�뒗 localhost:6379 媛 �
Windows 濡쒖뺄 Redis�뿉 癒쇱�� �뿰寃곕맆 �닔 �엳�쓬
(�씠誘 � �룷�듃瑜 � �젏�쑀�븯怨 � �엳湲 � �븣臾 �)

Docker Redis�룄 媛숈�� 6379 �룷�듃瑜 � �궗�슜�븯吏 � 留 �,
Windows�뿉�꽌 �씠誘 � �젏�쑀 以묒씠硫 � Docker�쓽 �룷�듃�룷�썙�뵫�씠 �젣��� 濡 � �룞�옉�븯吏 � �븡嫄곕굹
Spring Boot 媛 � 濡쒖뺄 Redis�뿉 �슦�꽑�쟻�쑝濡 � �뿰寃곕맖

吏꾨떒 怨쇱젙
docker exec -it <redis-container> redis-cli�뿉�꽌 keys \*瑜 � 爾ㅼ쓣 �븣 �썝�븯�뒗 �궎媛 � �븞 蹂댁엫

Spring Boot�뿉�꽌 RedisTemplate�쑝濡 � keys 瑜 � 李띿쑝硫 � �뜲�씠�꽣媛 � 蹂댁엫

tasklist | findstr redis 紐낅졊�쑝濡 � Windows�뿉 redis-server.exe 媛 � �뼚 �엳�뒗 嫄 � �솗�씤

利 �, Spring Boot 媛 � Docker Redis 媛 � �븘�땲�씪 Windows 濡쒖뺄 Redis�뿉 �뿰寃곕릺�뼱 �엳�뿀�쓬

�빐寃 � 諛 ⑸쾿
Windows�쓽 redis-server.exe �봽濡쒖꽭�뒪 醫낅즺

Windows 紐낅졊 �봽濡 ы봽�듃(cmd) �삉�뒗 PowerShell�뿉�꽌 �븘�옒 紐낅졊 �떎�뻾:

text
taskkill /F /IM redis-server.exe
�꽦怨 � 硫붿떆吏 � �삁�떆:

text
�꽦怨 �: �봽濡쒖꽭�뒪 "redis-server.exe"(PID xxxx)�씠(媛 �) 醫낅즺�릺�뿀�뒿�땲�떎.
Docker Redis 而 ⑦뀒�씠�꼫留 � �떎�뻾 �긽�깭濡 � �쑀吏 �

docker ps�뿉�꽌 0.0.0.0:6379->6379/tcp 媛 � �뼚 �엳�뒗吏 � �솗�씤

Spring Boot �옱�떆�옉

�씠�젣 localhost:6379 濡 � �젒�냽�븯硫 � Docker Redis�뿉留 � �뿰寃곕맖

1. Redis List ����옣/議고쉶 援 ъ“ �씠�빐
   ����옣 �떆:
   媛 � �엯李 � 濡쒓렇 媛앹껜(BidLogResponse �벑)瑜 �
   JSON 臾몄옄�뿴濡 � �븯�굹�뵫 Redis List�뿉 push�빐�빞 �븿.

議고쉶 �떆:
Redis List�쓽 媛 � �븘�씠�뀥�쓣
�떒�씪 媛앹껜濡 � �뿭吏곷젹�솕�빐�빞 �븿.

2. �옄二 � 諛쒖깮�븯�뒗 臾몄젣 諛 � �빐寃곕쾿

1) JSON 諛곗뿴 �쟾泥대�� �븳 踰덉뿉 ����옣
   臾몄젣:
   �쟾泥 � �엯李 � 濡쒓렇 由 ъ뒪�듃瑜 � JSON 諛곗뿴濡 � 吏곷젹�솕�빐
   由 ъ뒪�듃�뿉 �븳 踰덈쭔 push�븯硫 �,

議고쉶 �떆 �뿭吏곷젹�솕 �뿉�윭(MismatchedInputException: Cannot deserialize value of type ... from Array value)

�빐寃 �:
諛섎뱶�떆 for 臾 � �벑�쑝濡 � 媛 � 媛앹껜瑜 � �븯�굹�뵫 push
(�삁: for (BidLogResponse resp : responses) { ... })

2. Redis List 媛 � 鍮꾩뼱 �엳�뒗�뜲 罹먯떆 �뜲�씠�꽣媛 � �엳�떎怨 � �굹�샂
   �썝�씤:

LRANGE auction:1:logs -1 0 泥섎읆 start > stop�씠硫 � �빆�긽 鍮 � 諛곗뿴 諛섑솚

�떎�젣濡쒕뒗 LRANGE auction:1:logs 0 -1 濡 � �쟾泥 � 議고쉶�빐�빞 �븿

�빐寃 �:

�빆�긽 LRANGE <key> 0 -1 濡 � �쟾泥 � �뜲�씠�꽣 �솗�씤

�븷�뵆由 ъ���씠�뀡�뿉�꽌 Redis�뿉 媛믪씠 �뾾�쑝硫 � DB�뿉�꽌 �씫怨 �, 洹 � 寃곌낵瑜 � �떎�떆 罹먯떛�븯�뒗 援 ъ“�씤吏 � �솗�씤
