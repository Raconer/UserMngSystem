package com.module.prj.core.domain.exception

data class FieldError(
    val field: String,
    val reason: String
)