plugins{
    // JPA 생성자 초기화 제거 용도
    kotlin("plugin.jpa") version "1.9.24"
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")

    // DB
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}