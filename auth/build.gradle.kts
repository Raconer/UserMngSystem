plugins{
    // JPA 생성자 초기화 제거 용도
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("kapt")
}

dependencies {

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Validate
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DB
    runtimeOnly("com.mysql:mysql-connector-j")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // Map Struct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
}