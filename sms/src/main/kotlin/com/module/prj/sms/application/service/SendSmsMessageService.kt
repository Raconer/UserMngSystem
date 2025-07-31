package com.module.prj.sms.application.service

import com.module.prj.core.domain.sms.SmsMessage
import com.module.prj.sms.application.port.input.SendSmsMessageUseCase
import com.module.prj.sms.application.port.output.RedisTimeLimiterPort
import org.springframework.stereotype.Service

@Service
class SendSmsMessageService (
    private val redisTimeLimiter: RedisTimeLimiterPort
): SendSmsMessageUseCase{
    /**
     * SMS 메시지를 Redis 큐에 적재하여 제한 조건에 따라 전송되도록 한다.
     * @param smsMessage 전송할 SMS 메시지
     */
    override fun send(smsMessage: SmsMessage) {
        this.redisTimeLimiter.enqueueKakaoMessageToRedisQueue(smsMessage)
    }

}