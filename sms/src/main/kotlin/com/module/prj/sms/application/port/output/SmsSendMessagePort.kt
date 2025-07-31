package com.module.prj.sms.application.port.output

import com.module.prj.core.domain.sms.SmsMessage

interface SmsSendMessagePort {
    fun send(smsMessage: SmsMessage)
}