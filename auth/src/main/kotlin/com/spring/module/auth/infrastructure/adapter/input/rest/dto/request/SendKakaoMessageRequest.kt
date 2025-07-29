package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.NotNull

data class SendKakaoMessageRequest(
    @field:NotNull(message = "연령대는 필수입니다.")
    val ageGroup: Int? = null
) {
}