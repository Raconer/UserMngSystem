package com.spring.module.auth.application.port.output

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface UserRepositoryPort {

    // 사용자 저장 (회원가입)
    fun save(user: User): User

    // ID로 사용자 조회
    fun findById(id: Long): User?
    // 사용자 이름(username)으로 조회
    fun findByUsername(username: String): User?
    // 로그인용 사용자 정보 조회 (비밀번호 포함)
    fun findSignInfoByUsername(username: String): SignDTO?
    // 전체 사용자 페이지네이션 조회
    fun searchUsers(pageable:Pageable):Page<User>
    // 연령대 기준 사용자 목록 조회
    fun findByAgeGroup(ageGroup: Int): List<User>
    // 사용자 삭제
    fun deleteById(id: Long)
}