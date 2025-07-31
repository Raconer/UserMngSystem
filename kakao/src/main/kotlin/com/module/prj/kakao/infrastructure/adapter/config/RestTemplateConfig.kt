package com.module.prj.kakao.infrastructure.adapter.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    /**
     * 기본 설정된 RestTemplate 빈 생성
     * - Spring Boot에서 제공하는 Jackson 메시지 컨버터 자동 적용됨
     */
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build() // 내부에 Jackson 컨버터 자동 포함됨
    }
}