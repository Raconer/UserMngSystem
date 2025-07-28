package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>, UserQueryRepository{
    fun findByUsername(username: String): User?
}