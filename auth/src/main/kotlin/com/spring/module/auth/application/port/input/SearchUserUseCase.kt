package com.spring.module.auth.application.port.input

import com.spring.module.auth.domain.model.User
import com.module.prj.core.domain.common.PageResponse
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse

/**
 * 사용자 조회 관련 유스케이스 인터페이스
 * - 사용자 목록 및 상세 정보를 조회하는 기능 제공
 */
interface SearchUserUseCase {
    /**
     * 사용자 목록 페이징 조회
     * @param searchUserRequest 검색 조건 및 페이징 정보
     * @return 조회된 사용자 목록 페이지 응답
     */
    fun execute(searchUserRequest: SearchUserRequest): PageResponse<User>

    /**
     * 사용자 상세 정보 조회
     * @param username 사용자 ID 또는 username
     * @return 사용자 상세 정보 응답
     */
    fun getByUsername(username: String): UserInfoResponse
}