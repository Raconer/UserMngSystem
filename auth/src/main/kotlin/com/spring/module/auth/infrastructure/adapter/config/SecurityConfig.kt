package com.spring.module.auth.infrastructure.adapter.config

import com.spring.module.auth.infrastructure.adapter.config.security.JwtAuthenticationEntryPoint
import com.spring.module.auth.infrastructure.adapter.config.security.JwtRequestFilter
import com.spring.module.auth.infrastructure.adapter.config.security.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.Customizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtUtil: JwtUtil
){
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/user").permitAll()
                    .requestMatchers(HttpMethod.POST,"/sign/in").permitAll()
                    .requestMatchers("/admin/**").authenticated()
                    .anyRequest().denyAll()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .addFilterBefore(JwtRequestFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)


        return http.build()
    }

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