package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse

interface SignUseCase {
    fun signIn(signInRequest: SignInRequest): SignInResponse
}