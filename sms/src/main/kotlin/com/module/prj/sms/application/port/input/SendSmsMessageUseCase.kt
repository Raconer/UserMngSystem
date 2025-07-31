package com.module.prj.sms.application.port.input

import com.module.prj.sms.domain.model.SmsMessage

interface SendSmsMessageUseCase {
    fun send(smsSendRequest: SmsMessage)
}