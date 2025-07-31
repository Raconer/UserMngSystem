dependencies {
	// 공통 도메인/유틸 클래스 사용 (core 모듈 의존)
	implementation(project(":core"))

	// Spring Web 기능 사용 (REST API 개발 등)
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Kotlin 객체 ↔ JSON 직렬화/역직렬화 지원 (data class 대응 등)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")

	// REDIS
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// Kotlin Coroutine
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")
}