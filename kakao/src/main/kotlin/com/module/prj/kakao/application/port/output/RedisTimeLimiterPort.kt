package com.module.prj.kakao.application.port.output

import com.module.prj.kakao.domain.model.KakaoMessage

interface RedisTimeLimiterPort {
    fun enqueueKakaoMessageToRedisQueue(message: KakaoMessage)
    fun sendMessages()
}