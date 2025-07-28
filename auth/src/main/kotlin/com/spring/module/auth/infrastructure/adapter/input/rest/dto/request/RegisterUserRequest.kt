package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.*

data class RegisterUserRequest (
    @field:NotBlank(message = "아이디는 필수입니다.")
    val username: String? = null,
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min=8)
    val password: String? = null,
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String? = null,
    @field:NotNull(message = "주민등록번호는 필수입니다.")
    @field:Digits(integer = 13, fraction = 0, message = "주민등록번호는 숫자 13자리여야 합니다.")
    val rrn: String? = null,
    @field:NotBlank(message = "전화번호는 필수입니다.")
    @field:Pattern(
        regexp = "^\\d{10,11}$",
        message = "전화번호는 숫자 10~11자리여야 합니다."
    )
    val phoneNumber: String? = null,
    @field:NotBlank(message = "주소는 필수입니다.")
    val address: String? = null,
)