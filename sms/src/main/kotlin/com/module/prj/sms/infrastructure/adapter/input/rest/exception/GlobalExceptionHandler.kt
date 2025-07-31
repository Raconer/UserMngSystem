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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationEx(ex: MethodArgumentNotValidException ): ResponseEntity<Any> {

        val errors = ex.bindingResult.fieldErrors.map {
            FieldError(it.field, it.defaultMessage?: ResponseMessages.UNKNOWN_ERROR)
        }
        return CommonRes.Except(HttpStatus.BAD_REQUEST, errors)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception): ResponseEntity<Any> {
        val message = ex.message ?: ResponseMessages.UNKNOWN_ERROR
        return CommonRes.Except(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }
}