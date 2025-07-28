package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface UserQueryRepository {
    fun searchUsers(pageable: Pageable) : Page<User>
    fun findSignInfoByUsername(username: String): SignDTO?
}