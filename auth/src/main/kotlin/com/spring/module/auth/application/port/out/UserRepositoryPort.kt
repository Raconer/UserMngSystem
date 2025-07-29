package com.spring.module.auth.application.port.out

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface UserRepositoryPort {

    // CREATE
    // 회원 가입
    fun save(user: User): User

    // READ
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
    fun findSignInfoByUsername(username: String): SignDTO?
    fun searchUsers(pageable:Pageable):Page<User>
    fun findByAgeGroup(ageGroup: Int): List<User>

    // DELETE
    fun deleteById(id: Long)

}