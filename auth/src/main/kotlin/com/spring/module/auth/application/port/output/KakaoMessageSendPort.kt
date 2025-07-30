package com.spring.module.auth.application.port.output

import com.spring.module.auth.domain.model.KakaoMessage

interface KakaoMessageSendPort {
    fun send(kakaoMessage: KakaoMessage)
}