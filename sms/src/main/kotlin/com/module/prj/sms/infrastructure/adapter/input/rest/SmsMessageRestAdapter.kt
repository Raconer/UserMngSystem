package com.module.prj.sms.infrastructure.adapter.input.rest

import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendBodyRequest
import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendParamRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sms")
class SmsMessageRestAdapter {

    @PostMapping
    fun sendSmsMessage(
       @Valid @ModelAttribute smsSendParamRequest: SmsSendParamRequest,
       @Valid @RequestBody smsSendBodyRequest: SmsSendBodyRequest
    ): ResponseEntity<Void> {
        println("[Send Sms MSG API Param - phone] ${smsSendParamRequest}")
        println("[Send Sms MSG API Body ] ${smsSendBodyRequest}")
        return ResponseEntity.ok().build()
    }
}