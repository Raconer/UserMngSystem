# 사용자 관리 서비스 

## Modules
### 실행 방법

```shell
# 터미널 root 위치에서 아래 명령어 실행
./gradlew :auth:bootJar :kakao:bootJar :sms:bootJar

# 꼭 --build 필요
docker-compose up --build -d
```

### Auth 

````shell
auth/
├── AuthApplication.kt  
│   → [애플리케이션 진입점] Spring Boot 애플리케이션 main 클래스
│   → 목적: 서버 실행, 컴포넌트 스캔 시작 지점
│ 
├── application                   → [애플리케이션 계층] 유스케이스와 도메인 로직을 연결하는 계층
│ ├── mapper                      → DTO ↔ 도메인 모델 간 변환 로직
│ │ └── user
│ │     └── RegisterUserMapper.kt  
│ │     → [interface] MapStruct 기반 매핑 인터페이스 (RegisterUserRequest → User, User → Response)
│ │     → 목적: controller에서 받은 DTO를 domain entity로 변환하거나 반대로 변환
│ │
│ ├── port  → 유스케이스 인터페이스 정의 (헥사고날 아키텍처의 Port 역할)
│ │ ├── in  → 외부 입력 포트 (서비스에서 구현됨)
│ │ │ ├── RegisterUserUseCase.kt  
│ │ │ │     → [interface] 회원가입 기능을 정의한 유스케이스
│ │ │ ├── SearchUserUseCase.kt  
│ │ │ │     → [interface] 사용자 조회 유스케이스
│ │ │ └── SignUseCase.kt  
│ │ │       → [interface] 로그인 유스케이스
│ │ │
│ │ └── out  → 외부 출력 포트 (Repository 등)
│ │     └── UserRepositoryPort.kt  
│ │           → [interface] User 저장소 기능 정의 (DB 연결을 추상화)
│ │
│ └── service  → 포트 인터페이스를 구현한 실제 유스케이스 서비스
│     ├── GetUserService.kt  
│     │     → [class] SearchUserUseCase 구현체
│     ├── RegisterUserService.kt  
│     │     → [class] RegisterUserUseCase 구현체
│     └── SignService.kt  
│           → [class] SignUseCase 구현체
│ 
├── domain  → [도메인 계층] 핵심 비즈니스 모델 정의
│ ├── common
│ │ └── BasicEntity.kt  
│ │       → [abstract class] 생성일/수정일 등 공통 컬럼을 가진 엔티티 상위 클래스
│ │
│ └── model
│     └── User.kt  → [class] 회원 도메인 모델 (Entity)
│
└── infrastructure   → [인프라 계층] 외부 시스템과 연결 (DB, 보안, 설정 등)
    ├── adapter → 헥사고날 아키텍처의 Adapter 구현체
    │ ├── config  → 설정 클래스들
    │ │ ├── JpaConfig.kt      → [class] JPA 관련 설정
    │ │ ├── P6SpyConfig.kt    → [class] SQL 로그(P6Spy) 설정
    │ │ ├── QueryDslConfig.kt → [class] QueryDSL 설정
    │ │ ├── SecurityConfig.kt → [class] Spring Security 설정
    │ │ └── security
    │ │     ├── JwtAuthenticationEntryPoint.kt  
    │ │     │     → [class] 인증 실패 시 401 응답 반환
    │ │     ├── JwtRequestFilter.kt  
    │ │     │     → [class] JWT 필터 처리
    │ │     └── JwtUtil.kt  
    │ │           → [class] JWT 생성 및 검증 유틸
    │ │
    │ ├── input → 외부 입력 (Controller 역할)
    │ │ └── rest
    │ │     ├── AdminRestAdapter.kt → [@RestController] 관리자 API
    │ │     ├── SignRestAdapter.kt  → [@RestController] 로그인 API
    │ │     ├── UserRestAdapter.kt  → [@RestController] 사용자 API
    │ │     ├── common
    │ │     │ ├── CommonRes.kt    → [class] 공통 응답
    │ │     │ └── PageResponse.kt → [class] 페이징 결과 Wrapper
    │ │     ├── dto → API 요청/응답 DTO
    │ │     │ ├── ResponseMsg.kt  → [data class] 일반 응답 메시지 형식
    │ │     │ ├── exception
    │ │     │ │ └── FieldError.kt → [data class] Validation 실패 항목 리스트
    │ │     │ ├── request
    │ │     │ │ ├── RegisterUserRequest.kt  → 회원가입 요청 DTO
    │ │     │ │ ├── SearchUserRequest.kt    → 회원 조회 조건 DTO
    │ │     │ │ ├── SignInRequest.kt        → 로그인 요청 DTO
    │ │     │ │ └── UpdateUserRequest.kt    → 회원 수정 요청 DTO
    │ │     │ ├── response
    │ │     │ │ ├── SignInResponse.kt       → 로그인 결과 응답 DTO
    │ │     │ │ └── UserInfoResponse.kt     → 사용자 상세 응답 DTO
    │ │     │ └── sign
    │ │     │     └── SignDTO.kt            → 로그인 관련 공통 객체
    │ │     └── exception
    │ │         ├── CustomAuthenticationException.kt  → 사용자 정의 인증 예외
    │ │         ├── GlobalExceptionHandler.kt         → 전역 예외 핸들러
    │ │         ├── MissingUserIdException.kt         → 사용자 ID 누락 예외
    │ │         └── UserNotFoundException.kt          → 사용자 없음 예외
    │ │
    │ └── output  → 외부 출력 (DB 접근 등)
    │     └── persistence
    │         └── repository
    │             ├── UserJpaRepository.kt        → [interface] JpaRepository + Query 확장
    │             ├── UserQueryRepository.kt      → [interface] 사용자 조회용 QueryDSL 인터페이스
    │             ├── UserQueryRepositoryImpl.kt  → [class] QueryDSL 구현체
    │             └── UserRepositoryAdapter.kt    → [class] UserRepositoryPort 구현체 (DB 연결)
    │ 
    └── rest
        └── constant
            ├── GlobalConstants.kt  → [object] 전역 상수 정의 (예: prefix, token 등)
            └── ResponseMessages.kt → [object] 에러 메시지 등 공통 문자열 정의
````

### Kakao

```shell
kakao/
├── application                             → [애플리케이션 계층] 유스케이스 구현 및 포트 정의
│ ├── port                                → [Port 계층] 도메인과 인프라를 분리하기 위한 인터페이스 집합
│ │ ├── in
│ │ │ └── SendKakaoMessageUseCase.kt         → [interface] 외부에서 메시지 발송 요청 시 사용할 유스케이스 정의
│ │ └── out
│ │     └── KafkaMessageProducerPort.kt        → [interface] Kafka 메시지를 외부로 발행하기 위한 출력 포트 정의
│ └── service
│     └── SendKakaoMessageService.kt             → [class] SendKakaoMessageUseCase 구현체, Kafka 포트를 통해 메시지를 발행하는 핵심 로직 포함
│ 
├── domain                                   → [도메인 계층] 비즈니스 핵심 개념 표현
│ └── model
│     └── KakaoMessage.kt                        → [data class] 메시지 내용, 수신자, 템플릿 등의 도메인 모델 정의
│ 
└── infrastructure                          → [인프라 계층] 외부 시스템 연동 및 설정 구현체
    └── adapter                             → [어댑터 계층] 실제 외부와 연결되는 구현들
        ├── input                           → [입력 어댑터] 외부 요청을 받아 유스케이스 호출
        │ └── rest
        │     └── KakaoMessageRestAdapter.kt     → [@RestController] 메시지 발송을 요청받는 HTTP API 진입점
        ├── output                          → [출력 어댑터] 유스케이스에서 정의한 출력 포트를 구현
        │ └── producer
        │     └── KafkaMessageProducerAdapter.kt → [@Component] KafkaMessageProducerPort 구현, Kafka 토픽으로 메시지 발행
        └── input                           → [입력 어댑터] 외부에서 이벤트를 받아 처리
            └── consumer
                └── KafkaMessageConsumer.kt        → [@KafkaListener] Kafka 메시지를 수신하여 실제 카카오톡 메시지를 전송하거나 실패 시 SMS로 대체 발송 수행
```
