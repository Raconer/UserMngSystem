package com.module.prj.core.domain.common.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CommonRes {
    companion object{
        /**
         * 기본 응답 형태 - 결과값 없이 상태 코드만 반환
         * 예: ResponseUtil.Basic(HttpStatus.BAD_REQUEST)
         */
        fun Basic(httpStatus: HttpStatus): ResponseEntity<Any> {
            return ResponseEntity.ok(ResponseMsg(code = httpStatus.value(), message = httpStatus.name, result = null))
        }
        /**
         * 성공 응답 - HTTP 200 OK 상태로 result 포함
         * 예: ResponseUtil.Def(userDto)
         */
        fun Def(result: Any?): ResponseEntity<out Any> {
            val status = HttpStatus.OK
            return ResponseEntity.ok(ResponseMsg(code = status.value(), message = status.name, result = result))
        }
        /**
         * 예외 또는 커스텀 상태 반환 - 상태코드와 함께 결과 포함
         * 예: ResponseUtil.Except(HttpStatus.NOT_FOUND, "유저 없음")
         */
        fun Except(httpStatus: HttpStatus, result: Any?): ResponseEntity<Any> {
            return ResponseEntity.status(httpStatus).body(ResponseMsg(code = httpStatus.value(), message = httpStatus.name, result = result))
        }
    }
}