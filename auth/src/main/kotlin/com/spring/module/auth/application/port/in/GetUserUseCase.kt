package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.domain.model.User

interface GetUserUseCase {
    fun findAll(): List<User>
}