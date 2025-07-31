package com.module.prj.sms.application.port.output

import com.module.prj.core.domain.sms.SmsMessage

interface SmsSendMessagePort {
    /**
     * SMS 메시지 전송 처리
     * @param smsMessage 전송할 SMS 메시지 객체
     */
    fun send(smsMessage: SmsMessage)
}