package com.spring.module.auth.application.service

import com.spring.module.auth.application.mapper.user.RegisterUserMapper
import com.spring.module.auth.application.port.`in`.RegisterUserUseCase
import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val userRepositoryPort: UserRepositoryPort,
    private val registerUserMapper: RegisterUserMapper
) : RegisterUserUseCase {
    override fun register(registerUserRequest: RegisterUserRequest): User {
        val user = this.registerUserMapper.toEntity(registerUserRequest)
        return this.userRepositoryPort.save(user)
    }
}