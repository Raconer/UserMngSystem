package com.module.prj.kakao.application.port.output

import com.module.prj.core.domain.kakao.KakaoMessage

interface RedisTimeLimiterPort {
    /**
     * Redis 큐에 카카오 메시지 적재 (rate limit 적용 전 대기 큐)
     * @param kakaoMessage 전송 대기 중인 카카오 메시지
     */
    fun enqueueKakaoMessageToRedisQueue(kakaoMessage: KakaoMessage)
    /**
     * Redis에 저장된 메시지 중 호출 제한 범위 내에서 전송 실행
     */
    fun sendMessages()
}