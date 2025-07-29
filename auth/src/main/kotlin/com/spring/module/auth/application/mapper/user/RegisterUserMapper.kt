package com.spring.module.auth.application.mapper.user

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.domain.model.enum.GenderEnum
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse
import org.mapstruct.*
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(componentModel = "spring")
abstract class RegisterUserMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(
            target = "password",
            expression = "java(passwordEncoder.encode(request.getPassword()))"
        )
    )
    abstract fun toEntity(request: RegisterUserRequest, passwordEncoder: PasswordEncoder) : User

    @Mappings(
        Mapping(
            target = "address",
            expression = "java(user.getAddress().split(\" \")[0])"
        ),
        Mapping(target = "createdAt", source = "createdAt"),
        Mapping(target = "updatedAt", source = "updatedAt")
    )
    abstract fun toInfo(user: User): UserInfoResponse

    @AfterMapping
    protected fun mapDerivedFields(@MappingTarget user:User, request: RegisterUserRequest){
        val rrn = request.rrn!!.padStart(13, '0')

        val yearPrefix = when(rrn[6]){
            '1', '2' -> "19"
            '3', '4' -> "20"
            else -> throw IllegalArgumentException("올바르지 않은 주민등록번호입니다.")
        }
        val birthYear = (yearPrefix + rrn.substring(0, 2)).toInt()
        val gender = when (rrn[6]) {
            '1', '3' -> GenderEnum.MALE
            '2', '4' -> GenderEnum.FEMALE
            else -> throw IllegalArgumentException("올바르지 않은 성별 코드입니다.")
        }

        user.birthYear = birthYear
        user.gender = gender
    }
}