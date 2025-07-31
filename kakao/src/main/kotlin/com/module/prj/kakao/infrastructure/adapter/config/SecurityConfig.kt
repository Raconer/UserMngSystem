package com.module.prj.kakao.infrastructure.adapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

/**
 * Spring Security 설정
 * - 카카오 메시지 전송 API에 대해 인증 적용
 * - 기본 인증(Basic Auth) + Stateless 세션 정책
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {
    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder Bean 등록
     */
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    /**
     * HTTP 보안 설정
     * - /kakaotalk-messages 엔드포인트만 인증 허용
     * - 그 외 요청은 모두 차단
     * - CSRF 비활성화, 세션은 상태 없음(Stateless)
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it  .requestMatchers("/swagger/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/kakaotalk-messages").authenticated()
                    .anyRequest().denyAll()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }
    /**
     * 인메모리 사용자 등록
     * - username: autoever
     * - password: 1234 (BCrypt 암호화)
     */
    @Bean
    fun userDetailsService(): UserDetailsService {
        val admin: UserDetails = User
            .withUsername("autoever")
            .password(bCryptPasswordEncoder().encode("1234"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(admin)
    }
}