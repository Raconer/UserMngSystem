package com.module.prj.kakao.domain.model.event

import com.module.prj.core.domain.sms.SmsMessage

/**
 * SMS 메시지 전송 이벤트
 * - 카카오톡 전송 실패 시 대체 발송을 위한 SMS 메시지 목록을 담는 이벤트 객체
 *
 * @property smsMessageList 전송할 SMS 메시지 리스트
 */
data class SmsMessageEvent(
    val smsMessageList: List<SmsMessage>,
)