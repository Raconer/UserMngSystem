package com.module.prj.sms.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Schema(description = "SMS 전송 요청 파라미터")
data class SmsSendParamRequest(
    @field:Schema(description = "수신자 휴대폰 번호", example = "010-1234-5678")
    @field:NotBlank(message = "휴대폰 번호는 필수입니다.")
    @field:Pattern(
        regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$",
        message = "휴대폰 번호 형식이 올바르지 않습니다."
    )
    val phone: String? = null
)