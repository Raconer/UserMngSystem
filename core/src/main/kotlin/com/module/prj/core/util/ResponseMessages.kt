package com.module.prj.core.util

object ResponseMessages {
    // Exception Message
    const val UNKNOWN_ERROR =  "Unknown error"
    const val MISSING_USER_ID_ERROR =  "사용자 ID는 필수입니다."
    const val USER_NOT_FOUND = "사용자를 찾을수없습니다."
    const val PASSWORD_NOT_MATCH = "비밀번호를 잘못입력 하셨습니다."

    // JWT Exception
    const val TOKEN_IS_NULL = "토큰이 Null 입니다."
    const val AUTHENTICATION_FAILED = "인증에 실패하였습니다"
    const val INVALID_SIGNATURE = "잘못된 JWT 서명입니다"
    const val MALFORMED_TOKEN = "잘못된 JWT 토큰입니다"
    const val EXPIRED_TOKEN = "토큰 유효기간이 만료되었습니다"
    const val UNSUPPORTED_TOKEN = "지원되지 않는 JWT 토큰입니다"
    const val EMPTY_CLAIMS = "JWT claims 문자열이 비어있습니다"
}