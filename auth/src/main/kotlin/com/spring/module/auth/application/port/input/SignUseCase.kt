package com.spring.module.auth.application.port.input

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse

/**
 * 인증 관련 유스케이스 (로그인 등)
 */
interface SignUseCase {
    /**
     * 사용자 로그인 처리
     * @param signInRequest 로그인 요청 정보 (아이디, 비밀번호 등)
     * @return 로그인 결과 (JWT 토큰 등 포함)
     */
    fun signIn(signInRequest: SignInRequest): SignInResponse
}