package com.spring.module.auth.infrastructure.adapter.input.rest.dto.exception

data class FieldError(
    val field: String,
    val reason: String
) {

}
