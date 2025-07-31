package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>, UserQueryRepository {
    /**
     * username 으로 사용자 조회
     * @param username 사용자 ID
     * @return User 객체 또는 null
     */
    fun findByUsername(username: String): User?
}