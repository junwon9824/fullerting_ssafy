# 🌿 풀러팅 (Fullerting)

<div align="center">
  <img src="./img/etc/풀러팅.png" width="300"/>
  
  **도시농부를 위한 작물 거래 및 커뮤니티 플랫폼**
  
  [![React](https://img.shields.io/badge/React-18.2.0-61DAFB?logo=react)](https://reactjs.org/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-6DB33F?logo=spring-boot)](https://spring.io/projects/spring-boot)
  [![TypeScript](https://img.shields.io/badge/TypeScript-5.2.2-3178C6?logo=typescript)](https://www.typescriptlang.org/)
  [![Docker](https://img.shields.io/badge/Docker-20.10+-2496ED?logo=docker)](https://www.docker.com/)
  [![Jenkins](https://img.shields.io/badge/Jenkins-CI/CD-D24939?logo=jenkins)](https://jenkins.io/)
</div>

## 📋 목차

- [프로젝트 소개](#-프로젝트-소개)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [설치 및 실행](#-설치-및-실행)
- [API 문서](#-api-문서)
- [팀원 소개](#-팀원-소개)
- [협업 도구](#-협업-도구)
- [JWT 토큰 갱신 정책](#-jwt-토큰-갱신-정책)

## 🌟 프로젝트 소개

**풀러팅**은 도시농부들이 자신이 기른 작물을 거래하고, 재배 경험을 공유할 수 있는 종합 플랫폼입니다. 

### 핵심 가치
- 🥬 **신선한 작물 거래**: 동네 인증을 통한 신뢰할 수 있는 작물 거래
- 📝 **작물 재배 기록**: AI 기반 작물 인식과 함께하는 스마트 재배 일지
- 👥 **커뮤니티**: 농작물 재배 경험과 꿀팁 공유
- 🏡 **텃밭 정보**: 지역별 텃밭 위치 및 정보 제공

## 🚀 주요 기능

### 📱 메인화면

<div align="center">
  <img src="./img/main/메인화면.jpg" width="200" height="420"/>
  <img src="./img/main/알림확인.jpg" width="200" height="420"/>
</div>

- 홈, 작물거래, 커뮤니티, 작물일지, 마이페이지 네비게이션
- 실시간 알림 및 채팅 기능
- PWA 지원으로 앱과 같은 사용자 경험

### 🥬 작물거래

#### 동네인증
<div align="center">
  <img src="./img/trade/현재위치.jpg" width="200" height="420"/>
  <img src="./img/trade/주소인증.jpg" width="200" height="420"/>
</div>

#### 거래 시스템
<div align="center">
  <img src="./img/trade/거래.jpg" width="200" height="420"/>
  <img src="./img/trade/작물거래생성.jpg" width="200" height="420"/>
</div>

#### 제안 시스템
<div align="center">
  <img src="./img/trade/거래제안목록.jpg" width="200" height="420"/>
  <img src="./img/trade/거래제안.jpg" width="200" height="420"/>
</div>

#### 실시간 채팅
<div align="center">
  <img src="./img/trade/채팅목록.jpg" width="200" height="420"/>
  <img src="./img/trade/채팅.jpg" width="200" height="420"/>
</div>

**특징:**
- GPS 기반 동네 인증 시스템
- 일반거래 및 제안거래 방식 지원
- WebSocket 기반 실시간 채팅
- 거래 완료 자동 처리

### 📝 작물일지

#### 작물 관리
<div align="center">
  <img src="./img/diary/작물일지.jpg" width="200" height="420"/>
  <img src="./img/diary/작물일지생성.jpg" width="200" height="420"/>
</div>

#### 일기 작성
<div align="center">
  <img src="./img/diary/작물일기.jpg" width="200" height="420"/>
  <img src="./img/diary/다이어리상세.jpg" width="200" height="420"/>
  <img src="./img/diary/작물꿀팁.jpg" width="200" height="420"/>
</div>

#### AI 작물 인식
<div align="center">
  <img src="./img/diary/작물일지수확.jpg" width="200" height="420"/>
  <img src="./img/diary/작물일기수확2.jpg" width="200" height="420"/>
</div>

**특징:**
- TensorFlow 기반 작물 인식 AI
- 생육 단계별 자동 분류
- 수확 완료 시 뱃지 획득 시스템
- 작물별 맞춤 재배 팁 제공

### 👥 커뮤니티

<div align="center">
  <img src="./img/community/커뮤니티.jpg" width="200" height="420"/>
  <img src="./img/community/커뮤니티상세.jpg" width="200" height="420"/>
  <img src="./img/community/커뮤니티댓글.jpg" width="200" height="420"/>
</div>

**카테고리:**
- 자유게시판
- 작물소개
- 꿀팁공유
- 텃밭요리

### 🏡 텃밭정보

<div align="center">
  <img src="./img/farm/텃밭지도.jpg" width="200" height="420"/>
  <img src="./img/farm/텃밭상세.jpg" width="200" height="420"/>
</div>

- 카카오맵 API 기반 텃밭 위치 정보
- 지역별 텃밭 상세 정보 제공

### 👤 마이페이지

<div align="center">
  <img src="./img/mypage/프로필.jpg" width="200" height="420"/>
  <img src="./img/mypage/뱃지정보.jpg" width="200" height="420"/>
  <img src="./img/mypage/나의제안목록.jpg" width="200" height="420"/>
</div>

**기능:**
- 프로필 관리
- 뱃지 컬렉션
- 거래 내역 관리
- 관심 게시글 관리

## 🛠 기술 스택

### Frontend
| 기술 | 버전 | 용도 |
|------|------|------|
| React | 18.2.0 | UI 라이브러리 |
| TypeScript | 5.2.2 | 타입 안정성 |
| Vite | 5.1.6 | 빌드 도구 |
| Styled Components | 6.1.8 | CSS-in-JS |
| Jotai | 2.7.0 | 상태 관리 |
| TanStack Query | 5.28.6 | 서버 상태 관리 |
| React Router DOM | 6.22.3 | 라우팅 |
| React Kakao Maps SDK | 1.1.26 | 지도 서비스 |
| StompJS | 7.0.0 | WebSocket 통신 |

### Backend
| 기술 | 버전 | 용도 |
|------|------|------|
| Spring Boot | 3.2.3 | 웹 프레임워크 |
| Spring Security | - | 인증/인가 |
| Spring Data JPA | - | ORM |
| Spring WebSocket | - | 실시간 통신 |
| Spring Kafka | - | 메시지 큐 |
| JWT | 0.11.5 | 토큰 인증 |
| MySQL | - | 메인 데이터베이스 |
| Redis | - | 캐시/세션 |
| MongoDB | - | 채팅 로그 |
| QueryDSL | 5.0.0 | 동적 쿼리 |

### AI/ML
| 기술 | 버전 | 용도 |
|------|------|------|
| TensorFlow | 2.13.0 | 딥러닝 프레임워크 |
| Keras | 2.13.1 | 고수준 API |
| Django | 4.2.11 | AI 서버 |
| Teachable Machine | - | 모델 학습 |

### DevOps
| 기술 | 용도 |
|------|------|
| Docker | 컨테이너화 |
| Docker Compose | 멀티 컨테이너 관리 |
| Jenkins | CI/CD |
| Nginx | 리버스 프록시 |
| AWS | 클라우드 인프라 |

## 🏗 시스템 아키텍처

<div align="center">
  <img src="./아키텍쳐.drawio.png" alt="System Architecture" width="800"/>
</div>

### 아키텍처 특징
- **마이크로서비스**: Frontend, Backend, AI 서버 분리
- **실시간 통신**: WebSocket + Kafka 조합
- **스케일링**: Docker 컨테이너 기반 수평 확장
- **보안**: JWT + OAuth2 인증 시스템
- **모니터링**: Jenkins CI/CD 파이프라인

### 실시간 시스템 설계

#### Kafka + Redis 역할 분리
- **Kafka**: 실시간 메시지 전송 및 알림
  - 입찰 발생 시 즉시 Kafka로 메시지 전송
  - WebSocket을 통해 실시간 화면 업데이트
  - 파티션 키를 사용한 메시지 순서 보장

- **Redis**: 상태 캐시 및 빠른 조회
  - 경매 상태(현재가, 최고입찰자) 캐싱
  - 조회 시 DB 부하 감소
  - 1시간 만료 시간으로 메모리 관리

#### MongoDB: 입찰 로그 장기 보존 & 통계
- **Bid Log 영속 저장**: BidService 가 입찰 발생 시 `MongoTemplate.save()` 로 `bidLog` 컬렉션에 동시에 저장(upsert).  
- **집계 쿼리**: `BidRepositoryCustomImpl` 의 Aggregation 파이프라인으로 사용자 통계 등 계산.  
- **Schema-less**: 새로운 필드 추가 시 테이블 변경 없이 바로 반영.

##### Redis 해시 구조 예시 (auction:<경매글ID>)
```bash
# 예) 경매글 ID = 3
127.0.0.1:6379> HGETALL auction:3
1) "currentPrice"  # 현재 최고가
2) "8"
3) "bidLogId"      # 해당 입찰 로그(MongoDB) ID
4) "7"
5) "topBidderId"   # 최고 입찰자(사용자) ID
6) "2"
```
> BidService 에서 입찰이 발생할 때마다 위 해시를 갱신합니다. 프론트엔드·백엔드는 `HGETALL` 또는 `HVALS` 명령으로 값을 읽어 배열로 변환해 화면을 즉시 업데이트합니다.

##### Redis 입찰 로그 리스트 예시 (auction:<경매글ID>:logs)
```bash
LRANGE auction:3:logs 0 2   # 최신 3건 조회
1) "{\"bidLogId\":7,\"userId\":2,\"price\":8000, ... }"
2) "{\"bidLogId\":6,\"userId\":5,\"price\":7500, ... }"
3) "{\"bidLogId\":5,\"userId\":9,\"price\":7000, ... }"
```
> 두 키는 **독립적**으로 저장됩니다. 입찰 발생 시 서비스가 Hash 와 List 를 함께 갱신하지만, Redis 관점에서는 별도 엔트리이므로 만료시간/삭제를 개별 관리할 수 있습니다. Hash 는 현재 상태(O(1) 조회) , List 는 최근 로그 스트림(최대 50개) 역할을 합니다.

#### 입찰 현황 조회 동작
- **bidLogController.selectbid()** 로직
  1. `auction:{id}:logs` **Redis List** 에서 최근 입찰 로그 조회 → 있으면 바로 응답
  2. 비어 있을 경우 `bidService.selectbid()` 호출 → (캐시 해시 or DB) 조회 후 결과 반환
- **경매글(작물) 상세 페이지에 진입하면**
  - 프론트엔드가 selectbid API 호출
  - Controller 단계에서 Redis List 캐시 히트 시 즉시 반환->화면에 렌더링
  - 미스일 경우 Service 단계에서 Hash/DB 조회 후 결과 저장·반환
  - 결국 "렌더링하면 바로 보이는 입찰 정보"가 캐시 미스 없는 한 <100ms 내 응답

#### 동작 흐름
```
입찰 발생 시:
1. DB에 입찰 기록 저장
2. Redis에 상태 캐시 저장 (조회용)
3. MongoDB에 입찰 로그 영속 저장 (통계용)
4. Kafka로 실시간 메시지 전송 (알림용)

조회 시:
1. Redis에서 상태 우선 조회 (빠른 응답)
2. 없으면 DB에서 조회

실시간 알림 시:
1. Kafka 메시지로 WebSocket 전송
2. 화면 실시간 업데이트
```

#### 기술적 장점
- **성능 최적화**: Redis 캐시로 빠른 조회
- **실시간성**: Kafka + WebSocket으로 즉시 알림
- **확장성**: 각각 독립적으로 스케일링 가능
- **안정성**: 메시지 순서 보장 및 장애 복구

### 🚑 트러블슈팅 – ClassCastException(Integer → BidLogResponse)
| 증상 | 원인 | 해결 |
|------|------|------|
| `java.lang.ClassCastException: java.lang.Integer cannot be cast to BidLogResponse` | Redis 에 Hash 타입으로 `currentPrice`(숫자) 등을 저장한 뒤, 컨트롤러에서 **모든** Hash 값을 `BidLogResponse` 로 캐스팅함 | 1) Redis 구조를 **Hash(요약)** + **List(최근 로그)** 로 분리 2) 컨트롤러에서 `auction:{id}:logs` 리스트를 읽어 `instanceof` 검사 후 캐스팅 |
> 2025-07-06 리팩터링으로 Hash 에는 숫자·ID 등 요약 정보만, List 에는 `BidLogResponse` 객체만 저장하도록 변경했습니다. 컨트롤러는 먼저 List 를 조회하여 캐스팅 오류를 방지합니다.

**원인 상세**
1. 초기 구현에서는 `auction:{id}` 해시에 최근 입찰 로그 객체와 숫자 필드(현재가 등)를 **섞어** 저장했습니다.
2. 컨트롤러는 `HVALS auction:{id}` 로 모든 값을 조회한 뒤, 별도의 타입 체크 없이 `BidLogResponse` 로 강제 캐스팅했습니다.
3. 해시 안에 있던 `currentPrice`(Integer) 가 스트림 연산 중 캐스팅되어 `ClassCastException` 이 발생했습니다.

```java
List<BidLogResponse> cached = redisEntries.values().stream()
    .map(obj -> (BidLogResponse) obj) // Integer → BidLogResponse 캐스팅 오류
    .toList();
```
> 핵심 문제는 **단일 컨테이너(해시)에 다양한 타입**을 섞어 저장하면서도, 읽을 때 타입 구분을 하지 않은 설계였습니다.

## 🚀 Redis 캐싱 전략

### 1. 캐시 구조
- **경매 요약 정보 (Hash)**: `auction:{ex_article_id}`
  - `currentPrice`: 현재 최고 입찰가
  - `topBidderId`: 최고 입찰자 ID
  - `bidLogId`: 최근 입찰 로그 ID

- **입찰 로그 (List)**: `auction:{ex_article_id}:logs`
  - 최대 50개의 최근 입찰 기록 보관
  - 각 항목은 `BidLogResponse` 객체로 직렬화되어 저장

### 2. 캐시 업데이트 전략
- **쓰기 시 (입찰 제안)**:
  1. DB에 입찰 기록 저장
  2. Redis 캐시 업데이트
     - 경매 요약 정보 갱신
     - 입찰 로그 리스트에 새 입찰 추가 (최대 50개 유지)
  3. MongoDB에 백업 저장

- **읽기 시 (입찰 조회)**:
  1. Redis 캐시에서 조회 시도
  2. 캐시 미스 시 DB에서 조회 후 반환 (쓰기 시 캐시가 업데이트되므로 별도 캐시 갱신 X)

### 3. TTL (Time To Live)
- 모든 캐시 항목은 24시간 후 만료
- 자주 접근하는 항목은 만료 시간이 자동으로 갱신됨

### 4. 일관성 보장
- 모든 쓰기 작업은 DB와 Redis를 동시에 업데이트하여 일관성 유지
- Redis 오류 시에도 DB에서 정상 작동하도록 예외 처리

### 5. 성능 최적화
- 최대 50개의 입찰 로그만 캐시하여 메모리 사용량 제어
- 리스트 조회 시 범위를 지정하여 불필요한 데이터 전송 방지

## 🔐 JWT 토큰 갱신 정책

| 구분 | 내용 |
|------|------|
| Access Token | 유효기간 짧음(15 min). 모든 API 요청 시 `Authorization: Bearer <access>` 헤더로 전송 |
| Refresh Token | 유효기간 김(14 days). 최초 로그인 시 Redis 에 저장, Cookie/SessionStorage 에 보관 |

1. **요청 흐름**
   1) 클라이언트가 API 호출 → `JwtValidationFilter` 에서 accessToken 검증.
   2) 만료(401) → 프론트 Axios 인터셉터가 `/v1/auth/refresh` POST `{ refreshToken }` 호출.

2. **`TokenService.reIssueAccessTokenByRefreshToken()`**
   - refreshToken 서명·만료 검증 → `jwtUtils.validateRefreshToken()`
   - Claim 에서 `userId` 추출 → Redis 의 저장값과 일치 여부 확인.
   - 일치할 경우 **Refresh-Token Rotation** 수행:
     ```java
     String newAccess = jwtUtils.issueAccessToken(email, userId, authorities);
     String newRefresh = jwtUtils.issueRefreshToken(email, userId, authorities);
     tokenRepository.save(new Token(userId, newRefresh));          // Redis 갱신
     invalidTokenRepository.save(new InvalidToken(null, oldRefresh)); // 구 RT 폐기
     return new IssuedToken(newAccess, newRefresh);
     ```
   - 불일치 → `JwtException(JwtErrorCode.INVALID_TOKEN)` 반환.

3. **보안 효과**
   - 탈취된 refreshToken 재사용 시도 → Redis 불일치로 즉시 차단.
   - 이전 토큰은 블랙리스트 처리하여 일회성 보장.

> Rotation 이 부담스럽다면 새 refreshToken 발급·저장을 생략하고 기존 값을 그대로 반환하는 **Reuse 전략**으로 한 줄만 수정하면 됩니다.

## 💻 설치 및 실행

### 사전 요구사항
- Node.js 18+
- Java 17
- Docker & Docker Compose
- Python 3.8+ (AI 서버용)

### 1. 프로젝트 클론
```bash
git clone https://github.com/your-repo/fullerting.git
cd fullerting
```

### 2. Frontend 실행
```bash
cd frontend
npm install
npm run dev
```

### 3. Backend 실행
```bash
cd backend
./gradlew bootRun
```

### 4. AI 서버 실행
```bash
cd A.I
pip install -r requirements.txt
python manage.py runserver
```

### 5. Docker Compose 실행 (전체 서비스)
```bash
cd backend
docker-compose up -d
```

### 환경 변수 설정
프로젝트 루트에 `.env` 파일을 생성하고 다음 변수들을 설정하세요:

```env
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=fullerting
DB_USERNAME=root
DB_PASSWORD=password

# JWT
JWT_SECRET=your-jwt-secret
JWT_EXPIRATION=86400000

# Kakao OAuth
KAKAO_CLIENT_ID=your-kakao-client-id
KAKAO_CLIENT_SECRET=your-kakao-client-secret

# AWS S3
AWS_ACCESS_KEY=your-aws-access-key
AWS_SECRET_KEY=your-aws-secret-key
AWS_S3_BUCKET=your-s3-bucket

# Firebase FCM
FIREBASE_PROJECT_ID=your-firebase-project-id
```

## 📚 API 문서

### Swagger UI
- **개발 환경**: http://localhost:8080/swagger-ui.html
- **프로덕션**: https://api.fullerting.com/swagger-ui.html

### 주요 API 엔드포인트

#### 인증
```
POST /api/auth/login          # 로그인
POST /api/auth/refresh        # 토큰 갱신
GET  /api/auth/profile        # 프로필 조회
```

#### 작물 거래
```
GET    /api/trade/posts       # 거래 게시글 목록
POST   /api/trade/posts       # 거래 게시글 생성
GET    /api/trade/posts/{id}  # 거래 게시글 상세
POST   /api/trade/proposals   # 거래 제안
```

#### 작물 일지
```
GET    /api/diary/crops       # 작물 목록
POST   /api/diary/crops       # 작물 생성
PUT    /api/diary/crops/{id}  # 작물 수정
POST   /api/diary/recognize   # AI 작물 인식
```

#### 커뮤니티
```
GET    /api/community/posts   # 게시글 목록
POST   /api/community/posts   # 게시글 작성
GET    /api/community/posts/{id} # 게시글 상세
POST   /api/community/comments # 댓글 작성
```

## 👥 팀원 소개

| 이름 | 역할 | 담당 영역 | 기술 스택 |
|------|------|-----------|-----------|
| **김진명** (팀장) | Backend | 회원, 마이페이지, 알림 | Spring Boot, Spring Security, JWT |
| **문혜린** | Backend | 작물일지, 텃밭정보, 채팅 | Spring WebSocket, Kafka, MongoDB |
| **정준원** | Backend, DevOps | 작물거래, 커뮤니티, CI/CD | Spring Boot, Docker, Jenkins |
| **김나연** | Frontend | 회원, 작물일지, 텃밭정보 | React, TypeScript, Kakao Maps |
| **심우석** | Frontend | 메인페이지, 마이페이지, 커뮤니티 | React, Styled Components, Jotai |
| **오정민** | Frontend | 작물거래, 채팅 | React, WebSocket, TanStack Query |

## 🛠 협업 도구

### 개발 도구
- **버전 관리**: GitLab
- **이슈 관리**: Jira
- **문서 관리**: Notion
- **API 테스트**: Postman

### 문서 링크
- [📋 화면구성도](https://www.figma.com/file/Sknk6qQVE8fAiR5nOFvxza/%ED%92%80%EB%9F%AC%ED%8C%85?type=design&node-id=127-5825&mode=design&t=MnKBPQRoEeXjfoAR-0)
- [📄 페이지 명세서](https://www.notion.so/e6dd58e2958e4d87a058ba5411bdc34b?v=490bc367fa934dd6b4d8f99816e66ba6)
- [🧩 컴포넌트 명세서](https://www.notion.so/a1d316ad22c14e8d8615d9fd25b97608?v=a9f05331c88348239700d19d218dfb57)
- [🗄️ ERD](https://www.notion.so/ERD-dc7ce2874a2b4465b541f5cb0ce26b56?v=9deef6569fdd47b98a22de3c9d91ca21)

## 🛠 백엔드 설정

### Redis 설정

#### 의존성 추가 (pom.xml)
```xml
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```
 

#### Redis 데이터 구조
- `auction:{articleId}`: 경매 상태 정보 (Hash)
  - `bidLogId`: 마지막 입찰 ID
  - `topBidderId`: 최고 입찰자 ID
  - `currentPrice`: 현재 최고가

- `auction:{articleId}:logs`: 입찰 내역 (List)
  - 최대 50개 항목 유지
  - 1시간 TTL 설정

#### Redis CLI 명령어 예시
```bash
# 모든 경매 키 조회
KEYS auction:*

# 특정 경매의 상태 조회
HGETALL auction:3

# 입찰 내역 조회
LRANGE auction:3:logs 0 -1
```

### 🔄 Redis 캐싱 전략

#### 1. 캐시 구조
- **키 포맷**: 
  - `auction:{ex_article_id}`: 경매 메타데이터
  - `auction:{ex_article_id}:logs`: 최대 50개의 최근 입찰 내역

#### 2. 캐시 TTL
- **만료 시간**: 24시간
- **자동 갱신**: 캐시 미스 시 DB에서 데이터 조회 후 자동 갱신

#### 3. 캐시 일관성
- **쓰기 시**: 새로운 입찰 발생 시 캐시와 DB에 동시 저장
- **읽기 시**:
  1. Redis 캐시에서 조회 시도
  2. 캐시 미스 시 DB 조회 후 캐시 갱신

#### 4. 성능 최적화
- 최대 50개의 최근 입찰 내역만 캐시하여 메모리 사용량 제한
- 리스트 조회 시 캐시 우선 조회로 DB 부하 감소

### 🔄 Kafka Consumer Group

#### Consumer Group이 필요한 이유
1. **메시지 분산 처리**
   - 같은 Group ID를 가진 Consumer 인스턴스들이 토픽의 파티션을 나누어 처리
   - 예: 3개 파티션 토픽에 3개 Consumer가 같은 Group ID로 연결되면 각각 1개 파티션씩 담당

2. **메시지 중복/유실 방지**
   - Group ID를 기준으로 오프셋(offset) 관리
   - 메시지가 한 번만 처리되도록 보장

3. **확장성**
   - Consumer 수를 유연하게 조정 가능
   - Group Coordinator가 자동으로 파티션 재조정

4. **다양한 소비 패턴 지원**
   - 같은 토픽을 다른 Group ID로 구독하면 각 그룹이 독립적으로 모든 메시지 수신
   - 브로드캐스팅이 필요한 경우 유용

#### 주의사항
- `groupId`는 필수 속성입니다. 생략 시 "No group.id found in consumer config" 오류 발생
- 파티션 수 ≥ 컨슈머 수여야 모든 컨슈머가 메시지 수신
- 같은 파티션 내에서만 메시지 순서 보장

#### 예시 코드
```java
@KafkaListener(
    topics = "bid_requests", 
    groupId = "bid-group",  // 필수
    containerFactory = "bidKafkaListenerContainerFactory"
)
public void consumeBidRequest(String message) {
    // 메시지 처리 로직
}