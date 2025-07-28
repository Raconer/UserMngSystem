package com.spring.module.auth.application.service

import com.spring.module.auth.application.mapper.user.RegisterUserMapper
import com.spring.module.auth.application.port.`in`.RegisterUserUseCase
import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val userRepositoryPort: UserRepositoryPort,
    private val registerUserMapper: RegisterUserMapper,
    private val passwordEncoder: PasswordEncoder
) : RegisterUserUseCase {

    override fun register(registerUserRequest: RegisterUserRequest): User {
        val user = this.registerUserMapper.toEntity(registerUserRequest, passwordEncoder)
        return this.userRepositoryPort.save(user)
    }

    override fun update(id:Long, updateUserRequest: UpdateUserRequest): User {
        val user = this.userRepositoryPort.findById(id)?: throw UserNotFoundException()

        val updateUser = user.copy(
            password = updateUserRequest.password?.let { this.passwordEncoder.encode(it) } ?: user.password,
            address = updateUserRequest.address ?: user.address
        )

        return this.userRepositoryPort.save(updateUser)
    }

    override fun deleteById(id: Long) {
        this.userRepositoryPort.deleteById(id)
    }
}