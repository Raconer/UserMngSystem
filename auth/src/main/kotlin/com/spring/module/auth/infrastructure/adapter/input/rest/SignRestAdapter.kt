package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.input.SignUseCase
import com.spring.module.auth.infrastructure.adapter.input.rest.common.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign")
class SignRestAdapter(
    private val signUseCase: SignUseCase
) {
    @PostMapping("/in")
    fun signIn(@RequestBody @Valid signInRequest: SignInRequest): ResponseEntity<out Any> {
        return CommonRes.Def(this.signUseCase.signIn(signInRequest))
    }
}