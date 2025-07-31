package com.module.prj.sms.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.NotBlank
import io.swagger.v3.oas.annotations.media.Schema



@Schema(description = "SMS 전송 요청 본문")
data class SmsSendBodyRequest(
    @field:Schema(description = "전송할 메시지 내용", example = "안녕하세요, 테스트 메시지입니다.")
    @field:NotBlank(message = "메시지도 필수입니다.")
    val message: String
)
