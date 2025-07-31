package com.module.prj.core.domain.common.response

import java.time.LocalDate

data class ResponseMsg (
    val code: Int,
    val message: String,
    val date: LocalDate = LocalDate.now(),
    var result: Any? = null
)