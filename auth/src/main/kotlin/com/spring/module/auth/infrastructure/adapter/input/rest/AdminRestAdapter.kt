package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.`in`.RegisterUserUseCase
import com.spring.module.auth.application.port.`in`.SearchUserUseCase
import com.spring.module.auth.application.port.`in`.SendKakaoMessageUseCase
import com.spring.module.auth.infrastructure.adapter.input.rest.common.CommonRes
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SearchUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SendKakaoMessageRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.exception.MissingUserIdException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminRestAdapter(
    private val searchUserUseCase: SearchUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val sendKakaoMessageUseCase: SendKakaoMessageUseCase,
) {

    // 회원 조회
    @GetMapping
    fun searchUsers( searchUserRequest: SearchUserRequest): ResponseEntity<out Any> {
        return CommonRes.Def(this.searchUserUseCase.execute(searchUserRequest))
    }

    // 카카오톡
    @PostMapping("/kakao")
    fun sendMessageByAgeGroup(@RequestBody sendKakaoMessageRequest: SendKakaoMessageRequest): ResponseEntity<Any> {
        sendKakaoMessageUseCase.sendKakaoToAgeGroup(sendKakaoMessageRequest)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    // 수정
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<out Any> {
        if(id <= 0){
            throw MissingUserIdException()
        }

        return CommonRes.Def(this.registerUserUseCase.update(id, request))
    }

    // 삭제
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        if(id <= 0){
            throw MissingUserIdException()
        }

        this.registerUserUseCase.deleteById(id)

        return CommonRes.Basic(HttpStatus.OK)
    }
}