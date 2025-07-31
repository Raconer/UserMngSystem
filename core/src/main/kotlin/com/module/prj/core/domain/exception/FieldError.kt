package com.module.prj.core.domain.exception

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "필드 유효성 검사 실패 정보")
data class FieldError(
    @Schema(description = "유효성 검사가 실패한 필드명", example = "email")
    val field: String,
    @Schema(description = "검사 실패 사유", example = "이메일 형식이 아닙니다.")
    val reason: String
)