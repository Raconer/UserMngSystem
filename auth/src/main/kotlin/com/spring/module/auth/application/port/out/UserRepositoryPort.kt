package com.spring.module.auth.application.port.out

import com.spring.module.auth.domain.model.User

interface UserRepositoryPort {
    fun getAll(): List<User>
}