package com.spring.module.auth.application.port.out

import com.spring.module.auth.domain.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface UserRepositoryPort {
    // TODO : 삭제 예정
    fun getAll(): List<User>

    // CREATE
    // 회원 가입
    fun save(user: User): User

    // READ
    fun searchUsers(pageable:Pageable):Page<User>
}