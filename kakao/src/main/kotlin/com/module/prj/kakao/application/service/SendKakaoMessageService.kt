package com.module.prj.kakao.application.service

import com.module.prj.kakao.application.port.input.SendKakaoMessageUseCase
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import com.module.prj.kakao.domain.model.KakaoMessage
import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest
import org.springframework.stereotype.Service

@Service
class SendKakaoMessageService (
    private val redisTimeLimiter: RedisTimeLimiterPort
) : SendKakaoMessageUseCase {
    override fun send(kakaoSendRequest: KakaoSendRequest) {
        val kakaoMessage = KakaoMessage(kakaoSendRequest.phone!!, kakaoSendRequest.message!!)
        this.redisTimeLimiter.enqueueKakaoMessageToRedisQueue(kakaoMessage)
    }
}