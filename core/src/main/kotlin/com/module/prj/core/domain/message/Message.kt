package com.module.prj.core.domain.message

interface Message {
    val phone: String   // 수신자 전화번호
    val message: String // 발송할 메시지 본문
}