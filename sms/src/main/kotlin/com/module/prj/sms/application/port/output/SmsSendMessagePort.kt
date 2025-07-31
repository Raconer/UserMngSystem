package com.module.prj.sms.application.port.output

import com.module.prj.sms.domain.model.SmsMessage

interface SmsSendMessagePort {
    fun send(smsMessage: SmsMessage)
}