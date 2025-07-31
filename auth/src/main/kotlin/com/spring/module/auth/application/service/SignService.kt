package com.spring.module.auth.application.service

import com.spring.module.auth.application.port.input.SignUseCase
import com.spring.module.auth.application.port.output.UserRepositoryPort
import com.spring.module.auth.infrastructure.adapter.config.security.JwtUtil
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SignInRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.response.SignInResponse
import com.module.prj.core.common.ResponseMessages
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

    /**
     * 로그인 처리 로직
     * - 사용자 존재 여부 확인
     * - 비밀번호 검증
     * - JWT 토큰 발급
     */
    @Transactional(readOnly = true)
    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        val username = signInRequest.username!!

        // 사용자 조회
        val signDto = this.userRepositoryPort.findSignInfoByUsername(username)?: throw UsernameNotFoundException(
            ResponseMessages.USER_NOT_FOUND)

        if(!this.passwordEncoder.matches(signInRequest.password, signDto.password)){
            throw BadCredentialsException(ResponseMessages.PASSWORD_NOT_MATCH) // 비밀번호가 틀렸습니다로 작성하면 사용자 정보 노출 위험이 있으므로 사용자
        }

        // JWT 토큰 발급
        return SignInResponse(username, this.jwtUtil.create(username))
    }

}