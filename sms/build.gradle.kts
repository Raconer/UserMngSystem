dependencies {
    implementation(project(":core"))
    // Spring Web 기능 사용 (REST API 개발 등)
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")

    // REDIS
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")

}
