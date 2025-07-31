package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.input.RegisterUserUseCase
import com.spring.module.auth.application.port.input.SearchUserUseCase
import com.spring.module.auth.application.port.input.SendKakaoMessageUseCase
import com.module.prj.core.domain.common.response.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SendKakaoMessageRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.exception.MissingUserIdException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "관리자 API", description = "회원 관리 및 카카오톡 발송 관련 API")
@RestController
@RequestMapping("/admin")
class AdminRestAdapter(
    private val searchUserUseCase: SearchUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val sendKakaoMessageUseCase: SendKakaoMessageUseCase,
) {

    // 회원 조회
    @Operation(
        summary = "회원 목록 조회",
        description = "페이지 정보를 기반으로 전체 회원 목록을 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "500", description = "서버 오류")
        ]
    )
    @GetMapping
    fun searchUsers(    @Parameter(description = "회원 검색 요청 (페이지, 사이즈)", required = true)
                        searchUserRequest: SearchUserRequest): ResponseEntity<out Any> {
        return CommonRes.Def(this.searchUserUseCase.execute(searchUserRequest))
    }

    // 카카오톡
    @Operation(
        summary = "연령대별 카카오 메시지 발송",
        description = "연령대 정보를 받아 해당 연령대 사용자에게 카카오 메시지를 발송합니다.",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "연령대 정보",
            required = true,
            content = [io.swagger.v3.oas.annotations.media.Content(schema = io.swagger.v3.oas.annotations.media.Schema(implementation = SendKakaoMessageRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "전송 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "500", description = "서버 오류")
        ]
    )
    @PostMapping("/kakao")
    fun sendMessageByAgeGroup(@Valid @RequestBody sendKakaoMessageRequest: SendKakaoMessageRequest): ResponseEntity<Any> {
        this.sendKakaoMessageUseCase.sendKakaoToAgeGroup(sendKakaoMessageRequest)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    // 수정
    @Operation(
        summary = "회원 정보 수정",
        description = "ID로 회원 정보를 수정합니다.",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "수정할 회원 정보",
            required = true,
            content = [Content(schema = Schema(implementation = UpdateUserRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "수정 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "500", description = "서버 오류")
        ]
    )
    @PutMapping("/{id}")
    fun update(
        @Parameter(description = "회원 ID", required = true)
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<out Any> {
        if(id <= 0){
            throw MissingUserIdException()
        }

        return CommonRes.Def(this.registerUserUseCase.update(id, request))
    }

    // 삭제
    @Operation(
        summary = "회원 삭제",
        description = "ID로 회원을 삭제합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "삭제 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "500", description = "서버 오류")
        ]
    )
    @DeleteMapping("/{id}")
    fun delete(
        @Parameter(description = "회원 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        if(id <= 0){
            throw MissingUserIdException()
        }

        this.registerUserUseCase.deleteById(id)

        return CommonRes.Basic(HttpStatus.OK)
    }
}