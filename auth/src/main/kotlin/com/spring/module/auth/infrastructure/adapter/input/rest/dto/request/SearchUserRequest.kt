package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

data class SearchUserRequest(
    val page: Int = 1,
    val size: Int = 10
)