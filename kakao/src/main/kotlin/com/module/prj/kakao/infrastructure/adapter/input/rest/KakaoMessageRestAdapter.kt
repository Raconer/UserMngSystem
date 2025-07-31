package com.module.prj.kakao.infrastructure.adapter.input.rest

import com.module.prj.core.domain.common.response.ResponseMsg
import com.module.prj.core.domain.exception.FieldError
import com.module.prj.kakao.application.port.input.SendKakaoMessageUseCase
import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kakaotalk-messages")
class KakaoMessageRestAdapter(
    private val sendKakaoMessageUseCase: SendKakaoMessageUseCase
) {

    @Operation(
        summary = "카카오 메시지 전송",
        description = "수신자 번호와 메시지를 받아 카카오톡 메시지를 전송합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "전송 성공 (응답 본문 없음)"
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검사 실패)",
                content = [Content(schema = Schema(implementation = FieldError::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 내부 오류",
                content = [Content(schema = Schema(implementation = ResponseMsg::class))]
            )
        ]
    )
    @PostMapping
    fun sendKakaoMessage(@Valid @RequestBody kakaoSendRequest: KakaoSendRequest):ResponseEntity<Void> {
        this.sendKakaoMessageUseCase.send(kakaoSendRequest)
        return ResponseEntity.ok().build()
    }
}