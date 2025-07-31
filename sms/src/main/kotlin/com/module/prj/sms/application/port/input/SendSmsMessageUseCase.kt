package com.module.prj.sms.application.port.input

import com.module.prj.core.domain.sms.SmsMessage

interface SendSmsMessageUseCase {
    fun send(smsSendRequest: SmsMessage)
}