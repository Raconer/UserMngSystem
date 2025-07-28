package com.spring.module.auth.infrastructure.adapter.input.rest.dto

import java.time.LocalDate

data class ResponseMsg (
    val code: Int,
    val message: String,
    val date: LocalDate = LocalDate.now(),
    var result: Any? = null
)