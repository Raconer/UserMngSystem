# 사용자 관리 서비스 
> Spring Boot 기반의 사용자 관리 시스템입니다.  
> Hexagonal Architecture 및 멀티 모듈 구조를 사용하여 인증, 메시지 전송(Kakao/SMS) 기능을 분리하였습니다.
## Info
### 🚨 실행 방법 (중요) 🚨
```shell
# 터미널 root 위치에서 아래 명령어 실행
./gradlew :auth:bootJar :kakao:bootJar :sms:bootJar

# 꼭 --build 필요
docker-compose up --build -d
```

### 📡 서버 포트 안내
- auth 서버: http://localhost:8080
- kakao 서버: http://localhost:8081
- sms 서버: http://localhost:8082

### 📘 API 문서 (SWAGGER)
- Auth 서버:   http://localhost:8080/api/swagger/index.html
- Kakao 서버:  http://localhost:8081/swagger/index.html
- SMS 서버:    http://localhost:8082/swagger/index.html

## ROOT

### 📦 공통 기술 스택

#### 🛠️ 개발 환경
- Kotlin 1.9.25
- Java 17
- Gradle (Kotlin DSL)
- 멀티 모듈 구조: `auth`, `kakao`, `sms`, `core`

---

#### 🌐 프레임워크 & 웹
- Spring Boot 3.5.4
- Spring Web (REST API 개발)
- Spring Security (JWT 기반 인증/인가 처리)
- SpringDoc OpenAPI (Swagger UI 지원)

---

#### 🗃️ 데이터베이스 & ORM
- Spring Data JPA (ORM 매핑)
- MySQL 8 (Docker Compose 구성 포함)
- P6Spy (SQL 쿼리 포맷 가독성 향상)
- QueryDSL 5 (타입 안전한 쿼리 지원)

---

#### 🔁 DTO 변환 및 유효성 검증
- MapStruct 1.5.5 (DTO ↔ Entity 자동 매핑, kapt 기반)
- Bean Validation (JSR-380, `@Valid`, `@NotNull` 등)

---

#### 🔐 인증 및 토큰
- JWT (io.jsonwebtoken:jjwt)
    - API / Impl / Jackson 연동 구성
    - 토큰 생성, 검증, 파싱 처리

---

#### 🔍 운영 및 관측
- Spring Boot Actuator (헬스 체크, 메트릭, 상태 확인)
- Swagger UI (`/swagger-ui.html` 경로 제공)

---

#### 🧪 테스트
- JUnit 5 (`useJUnitPlatform()` 사용)
- Spring Boot Starter Test (통합 테스트 구성)
- Kotlin Test (`kotlin-test-junit5`)


## Modules
### 🧱 Core

```shell
core # 공통 도메인, DTO, 유틸, 포트 인터페이스 등을 담는 모듈
├── build.gradle.kts # core 모듈 전용 Gradle 빌드 설정 파일
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com.module.prj.core
    │   │       ├── CoreApplication.kt  
    │   │       ├── application
    │   │       │   └── port
    │   │       │       └── output     # 아웃고잉 포트 인터페이스 정의 (Hexagonal 아키텍처)
    │   │       ├── common
    │   │       │   ├── GlobalConstants.kt    # 공통 상수 정의
    │   │       │   └── ResponseMessages.kt   # 공통 응답 메시지 상수 정의
    │   │       ├── domain
    │   │       │   ├── common
    │   │       │   │   ├── PageResponse.kt     # 공통 페이징 응답 DTO
    │   │       │   │   └── response
    │   │       │   │       ├── CommonRes.kt    # API 공통 응답 DTO
    │   │       │   │       └── ResponseMsg.kt  # 결과 메시지 포맷 DTO
    │   │       │   ├── exception
    │   │       │   │   └── FieldError.kt       # 필드 유효성 검증 오류 DTO
    │   │       │   ├── kakao
    │   │       │   │   └── KakaoMessage.kt     # 카카오 메시지 도메인 객체
    │   │       │   ├── message
    │   │       │   │   └── Message.kt          # 공통 메시지 도메인 객체
    │   │       │   └── sms
    │   │       │       └── SmsMessage.kt       # SMS 메시지 도메인 객체
    │   │       └── infrastructure              # 공통 infra (ex. 공통 리포지토리 구현체 위치)
    │   └── resources
    │       └── application.properties          # core 모듈 설정 파일 (거의 비어있거나 테스트 용도)
    └── test
        └── kotlin
            └── com.module.prj.core
                └── CoreApplicationTests.kt     # core 모듈의 테스트 클래스 (기본 context 테스트용)
```

### 🧑‍💼 Auth 

````shell
auth                    # 사용자 인증 및 관리자 API 모듈 (회원가입, 로그인, 사용자 조회/수정/삭제, 카카오 메시지 발행 포함)
├── Dockerfile          # auth 모듈 컨테이너화를 위한 Docker 설정
├── build.gradle.kts    # Gradle 빌드 스크립트 (의존성, 컴파일 설정 등)
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com.spring.module.auth
    │   │       ├── AuthApplication.kt                      # Spring Boot 애플리케이션 진입점
    │   │       ├── application                             
    │   │       │   ├── mapper
    │   │       │   │   └── user
    │   │       │   │       └── RegisterUserMapper.kt       # 사용자 등록 시 DTO → 도메인 변환 처리
    │   │       │   ├── port
    │   │       │   │   ├── input                           # 유스케이스 인터페이스 (입력 포트)
    │   │       │   │   │   ├── RegisterUserUseCase.kt
    │   │       │   │   │   ├── SearchUserUseCase.kt
    │   │       │   │   │   ├── SendKakaoMessageUseCase.kt
    │   │       │   │   │   └── SignUseCase.kt
    │   │       │   │   └── output                           # 외부 저장소/시스템 접근을 위한 포트 인터페이스
    │   │       │   │       └── UserRepositoryPort.kt
    │   │       │   └── service                              
    │   │       │       ├── GetUserService.kt                # 사용자 조회 유스케이스 구현
    │   │       │       ├── RegisterUserService.kt           # 회원가입 유스케이스 구현
    │   │       │       ├── SendKakaoMessageService.kt       # 카카오 메시지 발행 유스케이스 구현
    │   │       │       └── SignService.kt                   # 로그인 및 인증 관련 유스케이스 구현
    │   │       ├── domain
    │   │       │   ├── common
    │   │       │   │   └── BasicEntity.kt                   # 공통 엔티티 (생성/수정 일자 포함)
    │   │       │   └── model
    │   │       │       ├── User.kt                          # 사용자 도메인 엔티티
    │   │       │       └── event
    │   │       │           └── KakaoMessageEvent.kt         # 카카오 메시지 전송 이벤트 도메인
    │   │       └── infrastructure
    │   │           └── adapter
    │   │               ├── config                           # 설정 파일
    │   │               │   ├── AsyncConfig.kt               # 비동기 설정
    │   │               │   ├── JpaConfig.kt                 # JPA 설정
    │   │               │   ├── P6SpyConfig.kt               # SQL 로그 출력 설정
    │   │               │   ├── QueryDslConfig.kt            # QueryDSL 설정
    │   │               │   ├── RestTemplateConfig.kt        # 외부 API 호출용 RestTemplate 설정
    │   │               │   ├── SecurityConfig.kt            # Spring Security 설정
    │   │               │   └── security
    │   │               │       ├── JwtAuthenticationEntryPoint.kt    # 인증 실패 시 처리
    │   │               │       ├── JwtRequestFilter.kt               # JWT 필터
    │   │               │       └── JwtUtil.kt                        # JWT 생성/파싱 유틸
    │   │               ├── input                             # 입력 어댑터 (Controller 등)
    │   │               │   ├── event
    │   │               │   │   └── KakaoMessageEventListener.kt  # Kakao 메시지 전송 이벤트 리스너
    │   │               │   └── rest                          # REST API 컨트롤러
    │   │               │       ├── AdminRestAdapter.kt           # 관리자용 API (회원 조회/수정/삭제)
    │   │               │       ├── SignRestAdapter.kt            # 로그인/회원가입 API
    │   │               │       ├── UserRestAdapter.kt            # 일반 사용자 API
    │   │               │       ├── dto                       # API 요청/응답 DTO
    │   │               │       │   ├── request
    │   │               │       │   │   ├── RegisterUserRequest.kt
    │   │               │       │   │   ├── SearchUserRequest.kt
    │   │               │       │   │   ├── SendKakaoMessageRequest.kt
    │   │               │       │   │   ├── SignInRequest.kt
    │   │               │       │   │   └── UpdateUserRequest.kt
    │   │               │       │   ├── response
    │   │               │       │   │   ├── SignInResponse.kt
    │   │               │       │   │   └── UserInfoResponse.kt
    │   │               │       │   └── sign
    │   │               │       │       └── SignDTO.kt
    │   │               │       └── exception
    │   │               │           ├── CustomAuthenticationException.kt
    │   │               │           ├── GlobalExceptionHandler.kt     # 전역 예외 처리
    │   │               │           ├── MissingUserIdException.kt
    │   │               │           └── UserNotFoundException.kt
    │   │               └── output
    │   │                   └── persistence                         # 영속성 어댑터
    │   │                       └── repository
    │   │                           ├── UserJpaRepository.kt         # Spring Data JPA Repository
    │   │                           ├── UserQueryRepository.kt       # 사용자 조회용 QueryDSL 인터페이스
    │   │                           ├── UserQueryRepositoryImpl.kt   # QueryDSL 구현체
    │   │                           └── UserRepositoryAdapter.kt     # UserRepositoryPort 구현체
    │   └── resources
    │       ├── application-docker.yml         # Docker 환경 설정
    │       ├── application-local.yml          # 로컬 실행 설정
    │       └── application.yml                # 공통 설정 (profile 별 override 대상)
````

#### 🧾 추가 스펙 요약

##### ✅ 공통 설정
- Kotlin + Spring Boot 기반 멀티 모듈 구조
- core 모듈의 공통 도메인 및 유틸 의존

---

##### 🗃️ DB 및 ORM
- MySQL 8 연동 (JDBC)
- Spring Data JPA 사용
- P6Spy로 쿼리 로그 가독성 향상
- QueryDSL 5 (Jakarta) 사용 + kapt 설정 포함

---

##### 🔐 인증 및 보안
- JWT 기반 인증 (jjwt-api + impl + jackson)
- Jackson 연동으로 payload 직렬화 처리

---

##### 🛠️ 매핑 및 직렬화
- MapStruct 사용 (DTO ↔ Entity 자동 매핑, kapt 사용)
- jackson-module-kotlin 사용 (Kotlin 객체 ↔ JSON 변환 대응)

---

##### 🔧 기타
- kotlin("plugin.jpa") 적용 → JPA 지연 초기화용 기본 생성자 제거

### 💬 Kakao

```shell
kakao                 # kakao 모듈 루트 디렉토리 (카카오 메시지 전송 전용 서비스)
├── Dockerfile        # kakao 모듈용 Docker 이미지 빌드 스크립트
├── build.gradle.kts  # kakao 모듈 전용 Gradle 설정 파일
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com.module.prj.kakao
    │   │       ├── KakaoApplication.kt                   # Spring Boot 진입점
    │   │       ├── application
    │   │       │   ├── port
    │   │       │   │   ├── input
    │   │       │   │   │   └── SendKakaoMessageUseCase.kt     # 카카오 메시지 전송 유스케이스 인터페이스
    │   │       │   │   └── output
    │   │       │   │       ├── KakaoSendMessagePort.kt        # 실제 카카오 API 호출을 위한 출력 포트
    │   │       │   │       └── RedisTimeLimiterPort.kt        # Redis 메시지 제한 체크 포트
    │   │       │   └── service
    │   │       │       └── SendKakaoMessageService.kt         # 유스케이스 구현체 (비즈니스 로직 포함)
    │   │       ├── domain
    │   │       │   └── model
    │   │       │       └── event
    │   │       │           └── SmsMessageEvent.kt             # SMS 대체 전송용 이벤트 객체 
    │   │       └── infrastructure
    │   │           └── adapter
    │   │               ├── config
    │   │               │   ├── CoroutineConfig.kt             # 코루틴 디스패처 등 동시성 처리 설정
    │   │               │   ├── RestTemplateConfig.kt          # 외부 API 호출을 위한 RestTemplate 설정
    │   │               │   ├── ScheduledConfig.kt             # 스케줄링 처리 설정
    │   │               │   └── SecurityConfig.kt              # 인증 우회 또는 최소 보안 설정
    │   │               ├── input
    │   │               │   ├── event
    │   │               │   │   └── SmsMessageEventListener.kt # SMS 전송 이벤트 수신 및 처리 (Listener)
    │   │               │   └── rest
    │   │               │       ├── KakaoMessageRestAdapter.kt     # REST API 컨트롤러
    │   │               │       ├── dto
    │   │               │       │   └── request
    │   │               │       │       └── KakaoSendRequest.kt   # 카카오 메시지 전송 요청 DTO
    │   │               │       └── exception
    │   │               │           └── GlobalExceptionHandler.kt # 예외 처리 전역 핸들러
    │   │               └── output
    │   │                   ├── KakaoSendMessageAdapter.kt        # 실제 카카오 API 호출 구현체
    │   │                   └── RedisTimeLimiterAdapter.kt        # Redis 호출 제한 처리 구현체
    │   └── resources
    │       ├── application-docker.yml       # Docker 환경 설정
    │       ├── application-local.yml        # 로컬 개발용 설정
    │       └── application.yml              # 공통 설정 파일
    └── test
        └── kotlin
            └── com.module.prj.kakao
                └── KakaoApplicationTests.kt # 통합 테스트 클래스 (Context 로딩 검증 등)
```
#### 🧾 추가 스펙 요약

##### ✅ 공통 설정
- core 모듈 의존 (공통 도메인/유틸 사용)
- Kotlin + Spring Boot 기반

---

##### 🌐 웹
- Spring Web 사용 (`spring-boot-starter-web`)
- REST API 서버 구성

---

##### 🔄 직렬화
- jackson-module-kotlin 사용 (Kotlin ↔ JSON 변환 최적화)

---

##### 🧠 Redis
- Spring Data Redis 사용 (`spring-boot-starter-data-redis`)
- 캐싱 또는 호출 제한 처리 등에 활용 가능

---

##### ⚙️ 코루틴
- Kotlin Coroutines Core / JDK8 사용
- 비동기 처리 및 스케줄 분산 처리 구조에 사용 가능

### 📲 SMS

```shell
sms   # SMS 대체 메시지 전송 전용 모듈
├── Dockerfile        # sms 모듈의 Docker 이미지 빌드 파일
├── build.gradle.kts  # Gradle 빌드 스크립트 (의존성, kapt, Redis 등 설정)
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com.module.prj.sms
    │   │       ├── SmsApplication.kt   # Spring Boot 실행 진입점
    │   │       ├── application
    │   │       │   ├── port
    │   │       │   │   ├── input
    │   │       │   │   │   └── SendSmsMessageUseCase.kt   # SMS 전송 유스케이스 인터페이스
    │   │       │   │   └── output
    │   │       │   │       ├── RedisTimeLimiterPort.kt    # Redis 기반 전송 제한 체크 포트
    │   │       │   │       └── SmsSendMessagePort.kt      # 실제 SMS API 전송 포트
    │   │       │   └── service
    │   │       │       └── SendSmsMessageService.kt       # 유스케이스 구현체 (비즈니스 로직)
    │   │       ├── domain
    │   │       │   └── model
    │   │       │       └── SmsMessage.kt                  # SMS 메시지 도메인 객체
    │   │       └── infrastructure
    │   │           └── adapter
    │   │               ├── config
    │   │               │   ├── CoroutineConfig.kt         # 코루틴 Dispatcher 설정
    │   │               │   ├── ScheduledConfig.kt         # 스케줄러 설정 (전송 재시도 등)
    │   │               │   └── SecurityConfig.kt          # 보안 최소 설정 또는 비활성화 설정
    │   │               ├── input
    │   │               │   └── rest
    │   │               │       ├── SmsMessageRestAdapter.kt      # SMS 전송 API Controller
    │   │               │       ├── dto
    │   │               │       │   └── request
    │   │               │       │       ├── SmsSendBodyRequest.kt   # 본문 방식 전송 요청 DTO
    │   │               │       │       └── SmsSendParamRequest.kt  # 파라미터 방식 전송 요청 DTO
    │   │               │       └── exception
    │   │               │           └── GlobalExceptionHandler.kt  # 공통 예외 처리 핸들러
    │   │               └── output
    │   │                   ├── RedisTimeLimiterAdapter.kt  # Redis 기반 호출 제한 구현체
    │   │                   └── SmsSendMessageAdapter.kt    # 실제 SMS API 호출 구현체
    │   └── resources
    │       ├── application-docker.yml                     # Docker 환경용 설정
    │       ├── application-local.yml                      # 로컬 실행 환경 설정
    │       └── application.yml                            # 공통 설정 (profile 별 override)
    └── test
        └── kotlin
            └── com.module.prj.sms
                └── SmsApplicationTests.kt                 # SpringBootTest 기반 통합 테스트
```

#### 🧾 추가 스펙 요약

##### ✅ 공통 설정
- core 모듈 의존 (공통 도메인/유틸 사용)
- Kotlin + Spring Boot 기반

---

##### 🌐 웹
- Spring Web 사용 (`spring-boot-starter-web`)
- REST API 서버 구성

---

##### 🔄 직렬화
- jackson-module-kotlin 사용 (Kotlin ↔ JSON 변환 최적화)

---

##### 🧠 Redis
- Spring Data Redis 사용 (`spring-boot-starter-data-redis`)
- 캐싱 또는 호출 제한 처리 등에 활용 가능

---

##### ⚙️ 코루틴
- Kotlin Coroutines Core / JDK8 사용
- 비동기 처리 및 스케줄 분산 처리 구조에 사용 가능