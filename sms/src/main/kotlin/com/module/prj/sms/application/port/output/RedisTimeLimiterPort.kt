package com.module.prj.sms.application.port.output

interface RedisTimeLimiterPort {
    fun enqueueKakaoMessageToRedisQueue(smsMessage: SmsMessage)
    fun sendMessages()
}