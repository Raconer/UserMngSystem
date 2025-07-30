package com.module.prj.kakao.infrastructure.adapter.input.rest

import com.module.prj.kakao.application.port.input.SendKakaoMessageUseCase
import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest
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
    @PostMapping
    fun sendKakaoMessage(@Valid @RequestBody kakaoSendRequest: KakaoSendRequest):ResponseEntity<Void> {
        println("sendKakaoMessage")
        this.sendKakaoMessageUseCase.send(kakaoSendRequest)
        return ResponseEntity.ok().build()
    }
}