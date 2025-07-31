package com.module.prj.sms.domain.model

import com.module.prj.core.domain.message.Message

data class SmsMessage (
    override val phone: String,
    override val message: String) : Message