package com.module.prj.core.domain.common

data class PageResponse<T>(
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLast: Boolean,
    val content: List<T>
)
