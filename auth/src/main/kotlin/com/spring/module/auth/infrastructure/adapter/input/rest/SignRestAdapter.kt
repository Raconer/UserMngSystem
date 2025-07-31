package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.input.SignUseCase
import com.module.prj.core.domain.common.response.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증 API", description = "로그인 관련 API")
@RestController
@RequestMapping("/sign")
class SignRestAdapter(
    private val signUseCase: SignUseCase
) {
    @Operation(
        summary = "로그인",
        description = "아이디와 비밀번호로 로그인을 시도합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [Content(schema = Schema(implementation = SignInResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검사 실패)"
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 내부 오류"
            )
        ]
    )
    @PostMapping("/in")
    fun signIn(
        @SwaggerRequestBody(
            description = "로그인 요청 정보",
            required = true,
            content = [Content(schema = Schema(implementation = SignInRequest::class))]
        )
        @RequestBody @Valid signInRequest: SignInRequest): ResponseEntity<out Any> {
        return CommonRes.Def(this.signUseCase.signIn(signInRequest))
    }
}