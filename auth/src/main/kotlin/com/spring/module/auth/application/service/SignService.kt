package com.spring.module.auth.application.service

import com.spring.module.auth.application.port.`in`.SignUseCase
import com.spring.module.auth.application.port.out.UserRepositoryPort
import com.spring.module.auth.infrastructure.adapter.config.security.JwtUtil
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse
import com.spring.module.auth.infrastructure.rest.constant.ResponseMessages
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val userRepositoryPort: UserRepositoryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) : SignUseCase {

    @Transactional(readOnly = true)
    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        val username = signInRequest.username!!
        val signDto = this.userRepositoryPort.findByUsername(username)?: throw UsernameNotFoundException(ResponseMessages.USER_NOT_FOUND)

        if(this.passwordEncoder.matches(signDto.password, username)){
            throw BadCredentialsException(ResponseMessages.PASSWORD_NOT_MATCH) // 비밀번호가 틀렸습니다로 작성하면 사용자 정보 노출 위험이 있으므로 사용자
        }

        return SignInResponse(username, this.jwtUtil.create(username))
    }
}