package com.spring.module.auth.infrastructure.adapter.config.security

import com.module.prj.core.common.ResponseMessages
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint{
    /**
     * 인증 실패 시 실행되는 메서드
     *
     * @param request 클라이언트의 HTTP 요청
     * @param response 클라이언트에 반환할 HTTP 응답
     * @param authException 인증 예외 정보
     */
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