package com.spring.module.auth.infrastructure.adapter.input.rest.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 응답")
data class SignInResponse(
    @Schema(description = "사용자 아이디", example = "testuser")
    var username:String,
    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR...")
    var token:String
)