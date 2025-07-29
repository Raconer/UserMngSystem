package com.spring.module.auth.application.port.`in`

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SendKakaoMessageRequest

interface SendKakaoMessageUseCase {
    fun sendKakaoToAgeGroup(request: SendKakaoMessageRequest)
}