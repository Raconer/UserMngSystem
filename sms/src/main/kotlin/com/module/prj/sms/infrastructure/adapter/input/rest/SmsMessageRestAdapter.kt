package com.module.prj.sms.infrastructure.adapter.input.rest

import com.module.prj.core.domain.sms.SmsMessage
import com.module.prj.sms.application.port.input.SendSmsMessageUseCase
import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendBodyRequest
import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendParamRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "SMS API", description = "SMS 메시지 전송 관련 API")
@RestController
@RequestMapping("/sms")
class SmsMessageRestAdapter(
    private val sendSmsMessageUseCase: SendSmsMessageUseCase
) {
    @Operation(
        summary = "SMS 메시지 전송",
        description = "휴대폰 번호와 메시지 내용을 받아 SMS를 전송합니다.",
        parameters = [
            Parameter(
                name = "phone",
                description = "수신자 휴대폰 번호",
                required = true,
                example = "010-1234-5678"
            )
        ],
        requestBody = SwaggerRequestBody(
            description = "SMS 메시지 본문",
            required = true,
            content = [Content(schema = Schema(implementation = SmsSendBodyRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "전송 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 오류)"),
            ApiResponse(responseCode = "500", description = "서버 오류")
        ]
    )
    @PostMapping
    fun sendSmsMessage(
       @Valid @ModelAttribute smsSendParamRequest: SmsSendParamRequest,
       @Valid @RequestBody smsSendBodyRequest: SmsSendBodyRequest
    ): ResponseEntity<Map<String, String>> {
        val smsMessage = SmsMessage(
            smsSendParamRequest.phone!!,
            smsSendBodyRequest.message
        )
        this.sendSmsMessageUseCase.send(smsMessage)
        return ResponseEntity.ok(mapOf("result" to "OK"))
    }
}