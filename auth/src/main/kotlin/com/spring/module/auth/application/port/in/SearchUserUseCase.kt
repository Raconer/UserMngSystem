package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.common.PageResponse
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse


interface SearchUserUseCase {
    fun execute(searchUserRequest: SearchUserRequest): PageResponse<User>

    // 사용자 정보 상세 조회
    fun getByUsername(username: String): UserInfoResponse
}