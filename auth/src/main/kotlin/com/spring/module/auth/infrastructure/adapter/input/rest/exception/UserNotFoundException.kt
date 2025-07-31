package com.spring.module.auth.infrastructure.adapter.input.rest.exception

import com.module.prj.core.common.ResponseMessages
/**
 * 사용자 ID 누락 시 발생하는 예외
 * - 예: 인증 토큰에 사용자 정보가 없을 때
 *
 * @param message 예외 메시지 (기본값: MISSING_USER_ID_ERROR)
 */
class UserNotFoundException(message: String = ResponseMessages.USER_NOT_FOUND, ) : RuntimeException(message)