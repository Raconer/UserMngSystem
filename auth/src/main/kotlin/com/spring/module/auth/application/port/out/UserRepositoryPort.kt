package com.spring.module.auth.application.port.out

import com.spring.module.auth.domain.model.User

interface UserRepositoryPort {
    // TODO : 삭제 예정
    fun getAll(): List<User>

    // 회원 가입
    fun save(user: User): User
}