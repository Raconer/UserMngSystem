package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

@Schema(description = "회원 가입 요청")
data class RegisterUserRequest (

    @field:Schema(description = "아이디", example = "testuser")
    @field:NotBlank(message = "아이디는 필수입니다.")
    val username: String? = null,

    @field:Schema(description = "비밀번호 (최소 8자)", example = "Abcd1234!")
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min=8)
    val password: String? = null,

    @field:Schema(description = "이름", example = "홍길동")
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String? = null,

    @field:Schema(description = "주민등록번호 (13자리 숫자)", example = "9001011234567")
    @field:NotNull(message = "주민등록번호는 필수입니다.")
    @field:Digits(integer = 13, fraction = 0, message = "주민등록번호는 숫자 13자리여야 합니다.")
    val rrn: String? = null,

    @field:Schema(description = "전화번호", example = "010-1234-5678")
    @field:NotBlank(message = "전화번호는 필수입니다.")
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
        message = "전화번호는 형식에 맞게 입력해야 합니다. 예) 010-1234-5678"
    )
    val phoneNumber: String? = null,

    @field:Schema(description = "주소", example = "서울시 강남구 테헤란로 1")
    @field:NotBlank(message = "주소는 필수입니다.")
    val address: String? = null,
)