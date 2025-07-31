package com.module.prj.sms.application.port.output

import com.module.prj.core.domain.sms.SmsMessage

interface RedisTimeLimiterPort {
    /**
     * Redis 큐에 SMS 메시지를 적재한다.
     * @param smsMessage 전송 대기할 SMS 메시지
     */
    fun enqueueKakaoMessageToRedisQueue(smsMessage: SmsMessage)
    /**
     * Redis 큐에 적재된 메시지를 제한에 따라 전송 처리한다.
     */
    fun sendMessages()
}