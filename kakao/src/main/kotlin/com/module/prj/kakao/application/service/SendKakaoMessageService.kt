package com.module.prj.kakao.application.service

import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.kakao.application.port.input.SendKakaoMessageUseCase
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest
import org.springframework.stereotype.Service

@Service
class SendKakaoMessageService (
    private val redisTimeLimiter: RedisTimeLimiterPort
) : SendKakaoMessageUseCase {
    /**
     * 클라이언트 요청을 받아 카카오 메시지 도메인으로 변환 후 Redis 큐에 등록
     * @param kakaoSendRequest 메시지 수신자 번호 및 본문을 포함한 요청 DTO
     */
    override fun send(kakaoSendRequest: KakaoSendRequest) {
        val kakaoMessage = KakaoMessage(kakaoSendRequest.phone!!, kakaoSendRequest.message!!)
        this.redisTimeLimiter.enqueueKakaoMessageToRedisQueue(kakaoMessage)
    }
}