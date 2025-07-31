package com.module.prj.sms.application.service

import com.module.prj.sms.application.port.input.SendSmsMessageUseCase
import com.module.prj.sms.application.port.output.RedisTimeLimiterPort
import org.springframework.stereotype.Service

@Service
class SendSmsMessageService (
    private val redisTimeLimiter: RedisTimeLimiterPort
): SendSmsMessageUseCase{
    override fun send(smsMessage: SmsMessage) {
        this.redisTimeLimiter.enqueueKakaoMessageToRedisQueue(smsMessage)
    }

}