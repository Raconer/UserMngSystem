package com.spring.module.auth.infrastructure.adapter.config

import com.spring.module.auth.infrastructure.adapter.config.security.JwtAuthenticationEntryPoint
import com.spring.module.auth.infrastructure.adapter.config.security.JwtRequestFilter
import com.spring.module.auth.infrastructure.adapter.config.security.JwtUtil
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtUtil: JwtUtil
){
    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder Bean 등록
     */
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    /**
     * Spring Security 필터 체인 설정
     * - CSRF, Form 로그인 비활성화
     * - 경로별 접근 권한 설정
     * - Stateless 세션 정책
     * - 인증 실패 시 사용자 정의 EntryPoint 적용
     * - UsernamePasswordAuthenticationFilter 앞에 JWT 필터 추가
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // CSRF 및 FormLogin 비활성화
            .csrf { it.disable() }
            .formLogin { it.disable() }
            // 경로별 접근 권한 설정
            .authorizeHttpRequests {
                it
                    .requestMatchers("/user").permitAll()
                    .requestMatchers(HttpMethod.POST,"/sign/in").permitAll()
                    .requestMatchers("/admin/**").authenticated()
                    .anyRequest().denyAll()
            }
            // HTTP Basic 인증 설정 (사용 X)
            .httpBasic(Customizer.withDefaults())
            // 세션 사용하지 않음 (JWT 기반)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            // 인증 실패 핸들링
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            // JWT 필터 추가
            .addFilterBefore(JwtRequestFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)


        return http.build()
    }


    /**
     * In-Memory 사용자(admin) 등록
     * - 시스템 관리자 API 사용을 위한 계정
     */
    @Bean
    fun userDetailsService(): UserDetailsService {
        val admin: UserDetails = User
            .withUsername("admin")
            .password(bCryptPasswordEncoder().encode("1212"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(admin)
    }

}