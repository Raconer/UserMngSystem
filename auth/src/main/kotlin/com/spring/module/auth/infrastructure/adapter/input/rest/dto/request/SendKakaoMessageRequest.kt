package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(description = "카카오 메시지 전송 요청")
data class SendKakaoMessageRequest(
    @field:Schema(description = "연령대 (예: 20, 30, 40)", example = "30")
    @field:NotNull(message = "연령대는 필수입니다.")
    val ageGroup: Int? = null
) {
}