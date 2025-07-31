plugins{
    // JPA 생성자 초기화 제거 용도
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("kapt")
}

dependencies {
    // 공통 도메인/유틸 의존성
    implementation(project(":core"))

    // Spring Web 기능 사용 (REST API 개발 등)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // MySQL DB 연동을 위한 JDBC 드라이버
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // JWT 인증
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // SQL 로그 출력용
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")

    // Kotlin 객체 ↔ JSON 직렬화/역직렬화
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // MapStruct 매핑 라이브러리 (DTO ↔ Entity 변환 자동화)
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
}