package com.module.prj.core.domain.common.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "공통 응답 포맷")
data class ResponseMsg (
    @Schema(description = "응답 코드 (HTTP status code)", example = "200")
    val code: Int,
    @Schema(description = "응답 메시지", example = "OK")
    val message: String,
    @Schema(description = "응답 날짜", example = "2025-07-31")
    val date: LocalDate = LocalDate.now(),
    @Schema(description = "응답 데이터")
    var result: Any? = null
)