package com.spring.module.auth.application.mapper.user

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.UserInfoResponse
import org.mapstruct.*
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(componentModel = "spring")
abstract class RegisterUserMapper {

    // 📌 RegisterUserRequest → User 엔티티로 변환
    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(
            target = "password",
            expression = "java(passwordEncoder.encode(request.getPassword()))" // 비밀번호는 암호화하여 저장
        )
    )
    abstract fun toEntity(request: RegisterUserRequest, passwordEncoder: PasswordEncoder) : User

    // 📌 User 엔티티 → UserInfoResponse DTO로 변환
    @Mappings(
        Mapping(
            target = "address",
            expression = "java(user.getAddress().split(\" \")[0])" // 전체 주소 중 첫 단어만 추출
        ),
        Mapping(target = "createdAt", source = "createdAt"),
        Mapping(target = "updatedAt", source = "updatedAt")
    )
    abstract fun toInfo(user: User): UserInfoResponse

    // 📌 매핑 이후 주민등록번호를 바탕으로 출생년도 계산
    @AfterMapping
    protected fun mapDerivedFields(@MappingTarget user:User, request: RegisterUserRequest){
        val rrn = request.rrn!!.padStart(13, '0')

        val yearPrefix = when(rrn[6]){
            '1', '2' -> "19"
            '3', '4' -> "20"
            else -> throw IllegalArgumentException("올바르지 않은 주민등록번호입니다.")
        }
        val birthYear = (yearPrefix + rrn.substring(0, 2)).toInt()

        user.birthYear = birthYear
    }
}