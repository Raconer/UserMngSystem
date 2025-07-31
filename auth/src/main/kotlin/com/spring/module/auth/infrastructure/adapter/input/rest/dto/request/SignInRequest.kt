package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

@Schema(description = "로그인 요청")
data class SignInRequest (
    @field:Schema(description = "사용자 아이디", example = "user")
    @field:NotEmpty
    val username: String? = null,
    @field:Schema(description = "비밀번호", example = "1q2w3e4r")
    @field:NotEmpty
    val password: String? = null
)