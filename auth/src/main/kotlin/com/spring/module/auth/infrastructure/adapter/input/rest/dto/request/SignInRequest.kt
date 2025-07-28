package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.NotEmpty

data class SignInRequest (
    @field:NotEmpty
    val username: String? = null,
    @field:NotEmpty
    val password: String? = null
)