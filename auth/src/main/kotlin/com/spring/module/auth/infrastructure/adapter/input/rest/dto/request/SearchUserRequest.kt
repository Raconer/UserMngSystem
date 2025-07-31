package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min

@Schema(description = "회원 검색 요청")
data class SearchUserRequest(
    @field:Schema(description = "페이지 번호 (1부터 시작)", example = "1")
    @field:Min(value = 1, message = "page는 1 이상이어야 합니다.")
    var page: Int = 1,
    @field:Schema(description = "페이지당 데이터 수", example = "10")
    @field:Min(value = 1, message = "size는 1 이상이어야 합니다.")
    var size: Int = 10
)