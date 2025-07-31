package com.module.prj.core.domain.sms

import com.module.prj.core.domain.message.Message

data class SmsMessage(
    override val phone: String,
    override val message: String) : Message