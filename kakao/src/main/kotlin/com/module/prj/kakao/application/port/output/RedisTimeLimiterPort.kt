package com.module.prj.kakao.application.port.output

import com.module.prj.core.domain.kakao.KakaoMessage

interface RedisTimeLimiterPort {
    fun enqueueKakaoMessageToRedisQueue(message: KakaoMessage)
    fun sendMessages()
}