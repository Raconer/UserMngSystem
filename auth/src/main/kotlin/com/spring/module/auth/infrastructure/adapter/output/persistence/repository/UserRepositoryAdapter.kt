package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.application.port.output.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component


@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserRepositoryPort{


    /**
     * 사용자 저장 (등록 또는 수정)
     */
    override fun save(user: User): User {
        return this.userJpaRepository.save(user)
    }

    /**
     * ID로 사용자 단건 조회 (없으면 null 반환)
     */
    override fun findById(id: Long): User? {
        return this.userJpaRepository.findByIdOrNull(id)
    }
    /**
     * username으로 사용자 조회 (없으면 null)
     */
    override fun findByUsername(username: String): User? {
        return this.userJpaRepository.findByUsername(username)
    }
    /**
     * 로그인용 사용자 정보 조회 (username, password)
     */
    override fun findSignInfoByUsername(username: String): SignDTO? {
        return this.userJpaRepository.findSignInfoByUsername(username)
    }

    /**
     * 페이징된 사용자 리스트 조회
     */
    override fun searchUsers(pageable: Pageable): Page<User> {
        return this.userJpaRepository.searchUsers(pageable)
    }

    /**
     * 특정 연령대 사용자 리스트 조회
     */
    override fun findByAgeGroup(ageGroup: Int): List<User> {
        return this.userJpaRepository.findByAgeGroup(ageGroup)
    }

    /**
     * 사용자 삭제
     */
    override fun deleteById(id: Long) {
        this.userJpaRepository.deleteById(id)
    }
}