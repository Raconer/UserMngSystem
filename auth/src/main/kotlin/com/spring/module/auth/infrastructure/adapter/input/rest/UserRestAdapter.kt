package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.`in`.GetUserUseCase
import com.spring.module.auth.application.port.`in`.RegisterUserUseCase
import com.spring.module.auth.infrastructure.adapter.input.rest.common.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserRestAdapter(
    private val getUserUseCase: GetUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) {

    // 회원가입
    @PostMapping
    fun signUp(@Valid @RequestBody registerUserRequest: RegisterUserRequest) : ResponseEntity<Any>{
        this.registerUserUseCase.register(registerUserRequest)
        return  CommonRes.Basic(HttpStatus.OK)
    }

    // 상세 조회
    @GetMapping
    fun getUser(): ResponseEntity<out Any> {
        return CommonRes.Basic(HttpStatus.OK)
    }

}