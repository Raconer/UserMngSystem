package com.module.prj.sms.infrastructure.adapter.output

import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.sms.application.port.output.SmsSendMessagePort
import com.module.prj.sms.domain.model.SmsMessage
import org.springframework.stereotype.Component

@Component
class SmsSendMessageAdapter() :SmsSendMessagePort {
    override fun send(smsMessage: SmsMessage) {
        try {
            println("SMS 메시지 전송 성공 : $smsMessage}")
        } catch (e: Exception) {
            println("❌ 전송 실패")
        }
    }
}