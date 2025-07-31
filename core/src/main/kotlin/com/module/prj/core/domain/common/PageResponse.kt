package com.module.prj.core.domain.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "페이징 응답 포맷")
data class PageResponse<T>(
    @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
    val page: Int,
    @Schema(description = "페이지당 항목 수", example = "10")
    val size: Int,
    @Schema(description = "전체 항목 수", example = "125")
    val totalElements: Long,
    @Schema(description = "전체 페이지 수", example = "13")
    val totalPages: Int,
    @Schema(description = "마지막 페이지 여부", example = "false")
    val isLast: Boolean,
    @Schema(description = "응답 데이터 리스트")
    val content: List<T>
)
