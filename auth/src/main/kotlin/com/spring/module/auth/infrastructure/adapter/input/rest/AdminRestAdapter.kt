package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.infrastructure.adapter.input.rest.common.CommonRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminRestAdapter {

    // 회원 조회
    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        return CommonRes.Basic(HttpStatus.OK)
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