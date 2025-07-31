package com.module.prj.kakao.infrastructure.adapter.input.rest.exception

import com.module.prj.core.domain.common.response.CommonRes
import com.module.prj.core.domain.exception.FieldError
import com.module.prj.core.common.ResponseMessages
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * @Valid, @NotBlank 등 유효성 검사 실패 처리
     * - 잘못된 필드명 및 오류 메시지를 FieldError 리스트로 변환
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationEx(ex: MethodArgumentNotValidException ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.map {
            FieldError(it.field, it.defaultMessage?: ResponseMessages.UNKNOWN_ERROR)
        }
        return CommonRes.Except(HttpStatus.BAD_REQUEST, errors)
    }

    /**
     * 그 외 예상하지 못한 예외 처리 (서버 내부 오류)
     * - 예외 메시지를 그대로 응답에 포함
     */
    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception): ResponseEntity<Any> {
        val message = ex.message ?: ResponseMessages.UNKNOWN_ERROR
        return CommonRes.Except(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }
}