package com.spring.module.auth.application.mapper.user

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(componentModel = "spring")
interface RegisterUserMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(
            target = "password",
            expression = "java(passwordEncoder.encode(request.getPassword()))"
        )
    )
    fun toEntity(request: RegisterUserRequest, passwordEncoder: PasswordEncoder) : User



    @Mappings(
        Mapping(
            target = "address",
            expression = "java(user.getAddress().split(\" \")[0])"
        ),
        Mapping(target = "createdAt", source = "createdAt"),
        Mapping(target = "updatedAt", source = "updatedAt")
    )
    fun toInfo(user: User): UserInfoResponse
}