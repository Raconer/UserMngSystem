package com.spring.module.auth.application.mapper.user

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface RegisterUserMapper {
    fun toEntity(request: RegisterUserRequest) : User
}