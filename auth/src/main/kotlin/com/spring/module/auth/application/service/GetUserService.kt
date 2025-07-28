package com.spring.module.auth.application.service

import com.spring.module.auth.application.port.`in`.SearchUserUseCase
import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val userRepositoryPort: UserRepositoryPort
) : SearchUserUseCase {

    override fun execute(searchUserRequest: SearchUserRequest): Page<User> {
        val pageable : Pageable = PageRequest.of(searchUserRequest.page - 1, searchUserRequest.size)
        return userRepositoryPort.searchUsers(pageable)
    }

}