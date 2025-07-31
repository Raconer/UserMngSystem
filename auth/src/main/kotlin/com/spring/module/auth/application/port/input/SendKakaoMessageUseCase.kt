package com.spring.module.auth.application.port.input

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SendKakaoMessageRequest

/**
 * 연령대별 사용자에게 카카오톡 메시지를 전송하는 유스케이스
 */
interface SendKakaoMessageUseCase {
    /**
     * 연령대 조건에 맞는 사용자에게 카카오톡 메시지를 전송
     * @param request 전송 대상 연령대 및 메시지 정보
     */
    fun sendKakaoToAgeGroup(request: SendKakaoMessageRequest)
}