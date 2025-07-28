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

    // 등록
    override fun save(user: User): User {
        return this.userJpaRepository.save(user)
    }

    // 단건 조회
    override fun findById(id: Long): User? {
        return this.userJpaRepository.findByIdOrNull(id)
    }

    // 사용자 조회 리스트
    override fun searchUsers(pageable: Pageable): Page<User> {
        return this.userJpaRepository.searchUsers(pageable)
    }

    // 사용자 정보 삭제
    override fun deleteById(id: Long) {
        this.userJpaRepository.deleteById(id)
    }
}