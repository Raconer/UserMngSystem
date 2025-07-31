package com.module.prj.sms.infrastructure.adapter.output

import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.core.domain.sms.SmsMessage
import com.module.prj.sms.application.port.output.SmsSendMessagePort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SmsSendMessageAdapter() :SmsSendMessagePort {
    private val log = LoggerFactory.getLogger(SmsSendMessageAdapter::class.java)
    /**
     * SMS 메시지 전송 시뮬레이션 메서드
     * @param smsMessage 전송할 SMS 메시지 객체
     */
    override fun send(smsMessage: SmsMessage) {
        try {
            log.info("SMS 메시지 전송 성공 : $smsMessage}")
        } catch (e: Exception) {
            log.error("❌ 전송 실패")
        }
    }
}