package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    @field:Size(min=8)
    val password: String? = null,
    val address: String? = null
)
