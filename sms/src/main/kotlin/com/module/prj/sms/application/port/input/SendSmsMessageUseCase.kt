package com.module.prj.sms.application.port.input

import com.module.prj.core.domain.sms.SmsMessage

interface SendSmsMessageUseCase {
    /**
     * SMS 메시지 전송 처리 메서드
     * @param smsMessage 전송할 SMS 메시지 정보
     */
    fun send(smsMessage: SmsMessage)
}