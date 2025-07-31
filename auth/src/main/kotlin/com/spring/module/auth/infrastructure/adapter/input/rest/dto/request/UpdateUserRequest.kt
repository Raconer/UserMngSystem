package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(description = "회원 정보 수정 요청")
data class UpdateUserRequest(
    @field:Schema(description = "비밀번호 (8자 이상)", example = "newPassword123")
    @field:Size(min=8)
    val password: String? = null,
    @field:Schema(description = "주소", example = "서울특별시 강남구 테헤란로 123")
    val address: String? = null
)
