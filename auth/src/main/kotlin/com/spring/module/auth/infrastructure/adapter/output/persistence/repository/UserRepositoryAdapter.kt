package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,

) : UserRepositoryPort{
    override fun getAll(): List<User> {
        return this.userJpaRepository.findAll()
    }

    override fun save(user: User): User {

        return this.userJpaRepository.save(user)
    }
}