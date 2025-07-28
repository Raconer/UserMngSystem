package com.spring.module.auth.application.service

import com.spring.module.auth.application.port.`in`.SearchUserUseCase
import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.common.PageResponse
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetUserService(
    private val userRepositoryPort: UserRepositoryPort
) : SearchUserUseCase {

    @Transactional(readOnly = true)
    override fun execute(searchUserRequest: SearchUserRequest): PageResponse<User> {
        val pageable : Pageable = PageRequest.of(searchUserRequest.page - 1, searchUserRequest.size)
        val pageResult =  userRepositoryPort.searchUsers(pageable)
        val userList = pageResult.content.map { it }
        return PageResponse(
            page = pageResult.number,
            size = pageResult.size,
            totalElements = pageResult.totalElements,
            totalPages = pageResult.totalPages,
            isLast = pageResult.isLast,
            content = userList,
        )
    }

}