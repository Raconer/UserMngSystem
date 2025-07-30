package com.spring.module.auth.application.port.input

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest

interface RegisterUserUseCase {
    fun register(registerUserRequest: RegisterUserRequest) : User
    fun update(id:Long, updateUserRequest: UpdateUserRequest) : User
    fun deleteById(id:Long)
}