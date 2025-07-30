package com.spring.module.auth.application.port.input

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse

interface SignUseCase {
    // 로그인
    fun signIn(signInRequest: SignInRequest): SignInResponse
}