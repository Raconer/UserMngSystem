package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.`in`.SearchUserUseCase
import com.spring.module.auth.infrastructure.adapter.input.rest.common.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminRestAdapter(
    private val searchUserUseCase: SearchUserUseCase
) {

    // 회원 조회
    @GetMapping
    fun searchUsers( searchUserRequest: SearchUserRequest): ResponseEntity<out Any> {
        val result = this.searchUserUseCase.execute(searchUserRequest)
        return CommonRes.Def(result)
    }

    // 수정
    @PutMapping("/{id}")
    fun update(): ResponseEntity<Any> {
        return CommonRes.Basic(HttpStatus.OK)
    }

    // 삭제
    @DeleteMapping("/{id}")
    fun delete(): ResponseEntity<Any> {
        return CommonRes.Basic(HttpStatus.OK)
    }
}