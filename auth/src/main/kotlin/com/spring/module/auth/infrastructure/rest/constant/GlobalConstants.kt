package com.spring.module.auth.infrastructure.rest.constant

object GlobalConstants {
    // JWT
    const val TOKEN_PREFIX: String = "Bearer "
    const val SUB_LEN: Int = this.TOKEN_PREFIX.length

    // MESSAGE
    const val SEND_KAKAO_MESSAGE = "%s님, 안녕하세요. 현대 오토에버입니다."
}