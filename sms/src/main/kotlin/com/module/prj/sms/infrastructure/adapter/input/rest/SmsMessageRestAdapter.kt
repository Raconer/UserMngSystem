package com.module.prj.sms.infrastructure.adapter.input.rest

import com.module.prj.sms.infrastructure.adapter.input.rest.dto.request.SmsSendRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sms")
class SmsMessageRestAdapter {

    @PostMapping
    fun sendKakaoMessage(@Valid @RequestBody smsSendRequest: SmsSendRequest): ResponseEntity<Void> {
        println("[Send Sms MSG API] ${smsSendRequest}")
        return ResponseEntity.ok().build()
    }
}