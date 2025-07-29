package com.spring.module.auth.application.port.out

import com.spring.module.auth.domain.model.KakaoMessage

interface KakaoMessageSendPort {
    fun send(message: KakaoMessage)
}