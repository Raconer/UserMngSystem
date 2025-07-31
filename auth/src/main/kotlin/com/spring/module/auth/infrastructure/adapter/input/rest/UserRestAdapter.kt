package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.input.RegisterUserUseCase
import com.spring.module.auth.application.port.input.SearchUserUseCase
import com.module.prj.core.domain.common.response.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserRestAdapter(
    private val registerUserUseCase: RegisterUserUseCase,
    private val searchUserUseCase: SearchUserUseCase
) {

    // 회원가입
    @PostMapping
    fun signUp(@Valid @RequestBody registerUserRequest: RegisterUserRequest) : ResponseEntity<out Any>{
        return CommonRes.Def(this.registerUserUseCase.register(registerUserRequest))
    }

    // 상세 조회
    @GetMapping
    fun getUser(@AuthenticationPrincipal signDTO: SignDTO): ResponseEntity<out Any> {

        return CommonRes.Def(this.searchUserUseCase.getByUsername(signDTO.username))
    }
}