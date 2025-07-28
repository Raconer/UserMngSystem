package com.spring.module.auth.infrastructure.adapter.input.rest.dto.response

data class SignInResponse(
    var username:String,
    var token:String
)