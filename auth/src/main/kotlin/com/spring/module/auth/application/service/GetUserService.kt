package com.spring.module.auth.application.service

import com.spring.module.auth.application.mapper.user.RegisterUserMapper
import com.spring.module.auth.application.port.input.SearchUserUseCase
import com.spring.module.auth.application.port.output.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.module.prj.core.domain.common.PageResponse
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse
import com.module.prj.core.util.ResponseMessages
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetUserService(
    private val userRepositoryPort: UserRepositoryPort,
    private val registerUserMapper: RegisterUserMapper
) : SearchUserUseCase {

    override fun getByUsername(username: String): UserInfoResponse {
        val user = this.userRepositoryPort.findByUsername(username)?: throw UsernameNotFoundException(ResponseMessages.USER_NOT_FOUND)
        return this.registerUserMapper.toInfo(user)
    }

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