plugins {
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.5.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false

}

allprojects {
    group = "com.user.manage"

    repositories {
        mavenCentral() // 필수: Kotlin stdlib 등 다운로드
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        // Kotlin 리플렉션 기능 사용 (예: 클래스 메타 정보 접근)
        add("implementation", "org.jetbrains.kotlin:kotlin-reflect")
        // Spring Security 기능 사용 (인증/인가 처리)
        add("implementation", "org.springframework.boot:spring-boot-starter-security")
        // Spring Boot Actuator 사용 (헬스 체크, 메트릭 등 운영 도구)
        add("implementation", "org.springframework.boot:spring-boot-starter-actuator")
        // Swagger UI 제공 (API 문서 자동 생성 및 웹 UI 제공)
        add("implementation", "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
        // Bean Validation(JSR-380) 사용 (예: @Valid, @NotNull 등)
        add("implementation", "org.springframework.boot:spring-boot-starter-validation")

        add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
        add("testImplementation", "org.jetbrains.kotlin:kotlin-test-junit5")
        add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")

    }
}