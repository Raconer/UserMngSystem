package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface UserQueryRepository {
    /**
     * 전체 사용자 페이지 조회
     * @param pageable 페이징 정보
     * @return 사용자 페이지 결과
     */
    fun searchUsers(pageable: Pageable) : Page<User>
    /**
     * username 기준 로그인용 정보 조회
     * @param username 사용자 ID
     * @return SignDTO (username, password)
     */
    fun findSignInfoByUsername(username: String): SignDTO?
    /**
     * 특정 연령대에 해당하는 사용자 목록 조회
     * @param ageGroup 연령대 (예: 20, 30, 40)
     * @return 해당 연령대 사용자 리스트
     */
    fun findByAgeGroup(ageGroup: Int): List<User>
}