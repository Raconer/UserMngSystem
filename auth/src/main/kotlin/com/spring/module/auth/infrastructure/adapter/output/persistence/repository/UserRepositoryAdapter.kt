package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component


@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,

) : UserRepositoryPort{

    override fun save(user: User): User {
        return this.userJpaRepository.save(user)
    }

    override fun findById(id: Long): User? {
        return this.userJpaRepository.findByIdOrNull(id)
    }

    // 사용자 조회 리스트
    override fun searchUsers(pageable: Pageable): Page<User> {
        return this.userJpaRepository.searchUsers(pageable)
    }
}