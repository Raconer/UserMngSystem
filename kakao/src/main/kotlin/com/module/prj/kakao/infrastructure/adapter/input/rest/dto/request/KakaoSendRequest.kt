package com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Schema(description = "카카오 메시지 전송 요청 DTO")
data class KakaoSendRequest(
    @field:NotBlank(message = "전화번호는 필수입니다.")
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
        message = "전화번호는 형식에 맞게 입력해야 합니다. 예) 010-1234-5678"
    )
    @Schema(description = "수신자 전화번호", example = "010-1234-5678", required = true)
    val phone:String? = null,
    @field:NotBlank(message = "메시지도 필수입니다.")
    @Schema(description = "보낼 메시지 내용", example = "홍길동님, 안녕하세요. 현대 오토에버입니다.", required = true)
    val message:String? = null
)