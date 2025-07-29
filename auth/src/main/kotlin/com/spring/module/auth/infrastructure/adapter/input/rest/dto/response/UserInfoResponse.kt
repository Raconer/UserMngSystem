package com.spring.module.auth.infrastructure.adapter.input.rest.dto.response

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