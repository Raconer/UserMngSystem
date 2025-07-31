package com.spring.module.auth.application.service

import com.spring.module.auth.application.mapper.user.RegisterUserMapper
import com.spring.module.auth.application.port.input.RegisterUserUseCase
import com.spring.module.auth.application.port.output.UserRepositoryPort
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterUserService(
    private val userRepositoryPort: UserRepositoryPort,
    private val registerUserMapper: RegisterUserMapper,
    private val passwordEncoder: PasswordEncoder
) : RegisterUserUseCase {

    /**
     * 사용자 등록
     */
    @Transactional
    override fun register(registerUserRequest: RegisterUserRequest): User {
        val user = this.registerUserMapper.toEntity(registerUserRequest, passwordEncoder)
        return this.userRepositoryPort.save(user)
    }

    /**
     * 사용자 정보 수정 (비밀번호, 주소)
     */
    @Transactional
    override fun update(id:Long, updateUserRequest: UpdateUserRequest): User {
        val user = this.userRepositoryPort.findById(id)?: throw UserNotFoundException()

        val updateUser = user.copy(
            password = updateUserRequest.password?.let { this.passwordEncoder.encode(it) } ?: user.password,
            address = updateUserRequest.address ?: user.address
        )

        return this.userRepositoryPort.save(updateUser)
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    override fun deleteById(id: Long) {
        this.userRepositoryPort.deleteById(id)
    }
}