# 사용자 관리 서비스 

## Modules

### Auth 

````shell
./
├── kotlin
│ └── com
│     └── spring
│         └── module
│             └── auth
│                 ├── AuthApplication.kt                         // 애플리케이션 진입점 (main 함수)
│                 ├── application                                 // 유즈케이스와 서비스 계층
│                 │ ├── mapper
│                 │ │ └── user
│                 │ │     └── RegisterUserMapper.kt          // RegisterUserRequest → User Entity 매핑 정의
│                 │ ├── port
│                 │ │ ├── in 
│                 │ │ │ ├── RegisterUserUseCase.kt        // 회원가입 UseCase 인터페이스 (INBOUND)
│                 │ │ │ └── SearchUserUseCase.kt          // 사용자 조회 UseCase 인터페이스 (INBOUND)
│                 │ │ └── out
│                 │ │     └── UserRepositoryPort.kt         // 사용자 저장/조회용 Port (OUTBOUND)
│                 │ └── service
│                 │     ├── GetUserService.kt                 // 사용자 목록 조회 서비스 (SearchUserUseCase 구현체)
│                 │     └── RegisterUserService.kt           // 사용자 등록 서비스 (RegisterUserUseCase 구현체)
│                 ├── domain
│                 │ ├── common
│                 │ │ └── BasicEntity.kt                   // 공통 Entity (createdAt, updatedAt 포함)
│                 │ └── model
│                 │     └── User.kt                          // 사용자 도메인 모델
│                 └── infrastructure                            // 외부 시스템 구현 계층
│                     ├── adapter
│                     │ ├── config
│                     │ │ ├── JpaConfig.kt                 // JPA 설정 (Auditing 등)
│                     │ │ ├── QueryDslConfig.kt            // QueryDSL 설정 (JPAQueryFactory 등)
│                     │ │ └── SecurityConfig.kt            // Spring Security 설정
│                     │ ├── input
│                     │ │ └── rest
│                     │ │     ├── AdminRestAdapter.kt      // 관리자용 API 컨트롤러
│                     │ │     ├── UserRestAdapter.kt       // 사용자 회원가입/조회 컨트롤러
│                     │ │     ├── common
│                     │ │     │ └── CommonRes.kt         // 공통 응답 포맷 처리 클래스
│                     │ │     ├── dto
│                     │ │     │ ├── ResponseMsg.kt       // 표준 응답 객체
│                     │ │     │ ├── exception
│                     │ │     │ │ └── FieldError.kt    // 필드 에러 응답 DTO
│                     │ │     │ └── request
│                     │ │     │     ├── RegisterUserRequest.kt // 회원가입 요청 DTO
│                     │ │     │     └── SearchUserRequest.kt   // 사용자 조회 요청 DTO
│                     │ │     └── exception
│                     │ │         └── GlobalExceptionHandler.kt // 글로벌 예외 처리
│                     │ └── output
│                     │     └── persistence
│                     │         └── repository
│                     │             ├── UserJpaRepository.kt       // ✅ Spring Data JPA Repository
│                     │             ├── UserQueryRepository.kt     // ✅ QueryDSL 커스텀 Repository 인터페이스
│                     │             ├── UserQueryRepositoryImpl.kt // ✅ QueryDSL 구현체
│                     │             └── UserRepositoryAdapter.kt   // ✅ RepositoryPort 구현체 (OUTBOUND ADAPTER)
│                     └── rest
│                         └── constant
│                             └── ResponseMessages.kt           // 응답 메시지 상수 정의
└── resources
    └── application.yml                                            // 환경설정 파일
````

#### Package 
