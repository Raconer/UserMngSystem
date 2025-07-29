package com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class KakaoSendRequest(
    @field:NotBlank(message = "전화번호는 필수입니다.")
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
        message = "전화번호는 형식에 맞게 입력해야 합니다. 예) 010-1234-5678"
    )
    val phone:String? = null,
    @field:NotBlank(message = "메시지도 필수입니다.")
    val message:String? = null
)