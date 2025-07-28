package com.spring.module.auth.infrastructure.adapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/user").permitAll()
                    .requestMatchers("/admin/**").authenticated()
                    .anyRequest().denyAll()
            }
            .httpBasic {}

        return http.build()
    }

}