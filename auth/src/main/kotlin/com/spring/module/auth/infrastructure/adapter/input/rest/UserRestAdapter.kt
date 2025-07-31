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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "회원 API", description = "회원가입 및 내 정보 조회 API")
@RestController
@RequestMapping("/user")
class UserRestAdapter(
    private val registerUserUseCase: RegisterUserUseCase,
    private val searchUserUseCase: SearchUserUseCase
) {

    // 회원가입
    @Operation(
        summary = "회원가입",
        description = "사용자 정보를 입력받아 회원가입을 수행합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "회원가입 성공"
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
    @PostMapping
    fun signUp(
        @SwaggerRequestBody(
            description = "회원가입 요청 정보",
            required = true,
            content = [Content(schema = Schema(implementation = RegisterUserRequest::class))]
        )
        @Valid @RequestBody registerUserRequest: RegisterUserRequest) : ResponseEntity<out Any>{
        return CommonRes.Def(this.registerUserUseCase.register(registerUserRequest))
    }

    // 상세 조회
    @Operation(
        summary = "내 정보 조회",
        description = "로그인한 사용자의 정보를 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "조회 성공"
            ),
            ApiResponse(
                responseCode = "401",
                description = "인증 실패 또는 토큰 누락"
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 내부 오류"
            )
        ]
    )
    @GetMapping
    fun getUser(@AuthenticationPrincipal signDTO: SignDTO): ResponseEntity<out Any> {

        return CommonRes.Def(this.searchUserUseCase.getByUsername(signDTO.username))
    }
}