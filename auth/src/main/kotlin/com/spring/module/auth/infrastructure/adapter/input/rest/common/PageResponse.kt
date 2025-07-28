package com.spring.module.auth.infrastructure.adapter.input.rest.common

data class PageResponse<T>(
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLast: Boolean,
    val content: List<T>
)
