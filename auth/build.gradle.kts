plugins{
    // JPA 생성자 초기화 제거 용도
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("kapt")
}

dependencies {

    // Spring Web 기능 사용 (REST API 개발 등)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JWT 인증을 위한 API (토큰 생성/파싱 등)
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    // JWT의 실제 구현체 (필수 runtime 구성 요소)
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    // JWT에서 JSON 파싱을 위한 Jackson 연동
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Bean Validation(JSR-380) 사용 (예: @Valid, @NotNull 등)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // MySQL DB 연동을 위한 JDBC 드라이버
    runtimeOnly("com.mysql:mysql-connector-j")

    // Spring Data JPA 사용 (엔티티 기반 ORM 처리)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // SQL 로그 출력용(P6Spy로 JPA 쿼리 로깅 보기 좋게)
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")

    // QueryDSL JPA 지원 (타입 안전한 쿼리 작성)
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    // QueryDSL Q타입 생성용 APT 프로세서
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // MapStruct 매핑 라이브러리 (DTO ↔ Entity 변환 자동화)
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    // MapStruct 컴파일 타임 프로세서 (자동 매퍼 생성용)
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
}