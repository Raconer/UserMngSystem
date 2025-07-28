package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import org.springframework.data.domain.Page

interface SearchUserUseCase {
    fun execute(searchUserRequest: SearchUserRequest): Page<User>
}