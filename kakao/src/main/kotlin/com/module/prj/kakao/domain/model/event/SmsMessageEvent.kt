package com.module.prj.kakao.domain.model.event

import com.module.prj.core.domain.sms.SmsMessage

data class SmsMessageEvent(
    val smsMessageList: List<SmsMessage>,
)