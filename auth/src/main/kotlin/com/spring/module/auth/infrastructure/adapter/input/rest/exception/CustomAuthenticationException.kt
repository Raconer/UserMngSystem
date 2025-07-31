package com.spring.module.auth.infrastructure.adapter.input.rest.exception

import org.springframework.security.core.AuthenticationException

/**
 * JWT 인증 처리 중 발생할 수 있는 사용자 정의 예외 클래스.
 * Spring Security의 AuthenticationException을 상속하여 보안 필터에서 처리 가능하게 한다.
 */
class CustomAuthenticationException : AuthenticationException {
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
    constructor(msg: String?) : super(msg)
}