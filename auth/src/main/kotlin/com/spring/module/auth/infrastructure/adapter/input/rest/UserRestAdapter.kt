package com.spring.module.auth.infrastructure.adapter.input.rest

import com.spring.module.auth.application.port.`in`.GetUserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestAdapter(
    private val getUserUseCase: GetUserUseCase,
) {
    @GetMapping
    fun get() {
        println("GET!!! User!!!")
        val data = this.getUserUseCase.findAll()
        println(data)
    }
}