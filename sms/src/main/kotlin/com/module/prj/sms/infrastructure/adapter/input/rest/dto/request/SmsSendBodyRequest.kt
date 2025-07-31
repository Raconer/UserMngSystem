package com.module.prj.sms.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.NotBlank

data class SmsSendBodyRequest(
    @field:NotBlank(message = "메시지도 필수입니다.")
    val message: String
)
