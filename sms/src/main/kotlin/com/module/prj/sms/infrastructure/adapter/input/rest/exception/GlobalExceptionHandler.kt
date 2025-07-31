package com.module.prj.sms.infrastructure.adapter.input.rest.exception

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
     * @param ex 유효성 검사 실패 예외
     * @return 필드별 에러 메시지를 담아 400 BAD_REQUEST 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationEx(ex: MethodArgumentNotValidException ): ResponseEntity<Any> {

        val errors = ex.bindingResult.fieldErrors.map {
            FieldError(it.field, it.defaultMessage?: ResponseMessages.UNKNOWN_ERROR)
        }
        return CommonRes.Except(HttpStatus.BAD_REQUEST, errors)
    }


    /**
     * @param ex 모든 예외 처리
     * @return 500 INTERNAL_SERVER_ERROR 응답 반환
     */
    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception): ResponseEntity<Any> {
        val message = ex.message ?: ResponseMessages.UNKNOWN_ERROR
        return CommonRes.Except(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }
}