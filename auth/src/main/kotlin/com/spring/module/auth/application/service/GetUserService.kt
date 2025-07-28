package com.spring.module.auth.application.service

import com.spring.module.auth.application.port.`in`.GetUserUseCase
import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val userRepositoryPort: UserRepositoryPort
) : GetUserUseCase {
    override fun findAll(): List<User> {
        return this.userRepositoryPort.getAll()
    }
}