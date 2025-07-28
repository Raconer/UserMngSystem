package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse

interface SignUseCase {
    // 로그인
    fun signIn(signInRequest: SignInRequest): SignInResponse
}