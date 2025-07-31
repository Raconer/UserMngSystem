package com.module.prj.sms.infrastructure.adapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()


    /**
     * Spring Security HTTP 보안 필터 체인 설정
     * - CSRF 비활성화
     * - POST /sms 요청만 인증 필요
     * - 나머지 요청은 모두 거부
     * - HTTP Basic 인증 사용
     * - 세션을 생성하지 않는 무상태(stateless) 정책 적용
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/swagger/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/sms").authenticated()
                    .anyRequest().denyAll()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }


    /**
     * 인메모리 사용자 인증 서비스 등록
     */
    @Bean
    fun userDetailsService(): UserDetailsService {
        val admin: UserDetails = User
            .withUsername("autoever")
            .password(bCryptPasswordEncoder().encode("5678"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(admin)
    }
}