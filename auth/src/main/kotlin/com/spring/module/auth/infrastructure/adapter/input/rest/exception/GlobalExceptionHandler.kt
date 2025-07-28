package com.spring.module.auth.infrastructure.adapter.input.rest.exception

import com.spring.module.auth.infrastructure.adapter.input.rest.common.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.exception.FieldError
import com.spring.module.auth.infrastructure.rest.constant.ResponseMessages
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
}