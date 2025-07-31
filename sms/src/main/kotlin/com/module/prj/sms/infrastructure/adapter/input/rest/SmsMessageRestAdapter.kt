package com.module.prj.sms.infrastructure.adapter.input.rest

import com.module.prj.sms.application.port.input.SendSmsMessageUseCase
import com.module.prj.sms.domain.model.SmsMessage
import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendBodyRequest
import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendParamRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sms")
class SmsMessageRestAdapter(
    private val sendSmsMessageUseCase: SendSmsMessageUseCase
) {

    @PostMapping
    fun sendSmsMessage(
       @Valid @ModelAttribute smsSendParamRequest: SmsSendParamRequest,
       @Valid @RequestBody smsSendBodyRequest: SmsSendBodyRequest
    ): ResponseEntity<Void> {
        val smsMessage = SmsMessage(
            smsSendParamRequest.phone!!,
            smsSendBodyRequest.message
        )
        this.sendSmsMessageUseCase.send(smsMessage)
        return ResponseEntity.ok().build()
    }
}