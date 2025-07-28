package com.spring.module.auth.infrastructure.adapter.input.rest.dto.request

data class SearchUserRequest(
    var page: Int = 1,
    var size: Int = 10
)