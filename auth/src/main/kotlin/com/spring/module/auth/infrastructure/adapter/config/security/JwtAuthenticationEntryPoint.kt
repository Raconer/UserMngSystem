package com.spring.module.auth.infrastructure.adapter.config.security

import com.spring.module.auth.infrastructure.rest.constant.ResponseMessages
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint{
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        val writer = response.writer
        writer.write("{\n" +
                "    \"code\": ${HttpStatus.UNAUTHORIZED.value()},\n" +
                "    \"message\": \"${HttpStatus.UNAUTHORIZED.name}\",\n" +
                "    \"date\": \"${LocalDate.now()}\",\n" +
                "    \"result\": \"${ResponseMessages.USER_NOT_FOUND}\"\n" +
                "}")
        writer.flush()
    }
}