package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.rest.constant.ResponseMessages

interface RegisterUserUseCase {
    fun register(registerUserRequest: RegisterUserRequest) : User
}