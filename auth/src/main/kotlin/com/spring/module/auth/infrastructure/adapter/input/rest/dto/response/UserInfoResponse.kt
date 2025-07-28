package com.spring.module.auth.infrastructure.adapter.input.rest.dto.response

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

data class UserInfoResponse(
    val username:String,
    var password:String,
    val name:String,
    val rrn:String,
    val phoneNumber:String,
    val address:String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)