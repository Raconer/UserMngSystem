package com.spring.module.auth.application.port.input

import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest

/**
 * 사용자 등록/수정/삭제를 위한 유스케이스 인터페이스
 */
interface RegisterUserUseCase {
    /**
     * 신규 사용자 등록
     * @param registerUserRequest 사용자 등록 요청 DTO
     * @return 등록된 사용자 엔티티
     */
    fun register(registerUserRequest: RegisterUserRequest) : User
    /**
     * 사용자 정보 수정
     * @param id 수정 대상 사용자 ID
     * @param updateUserRequest 수정 요청 DTO
     * @return 수정된 사용자 엔티티
     */
    fun update(id:Long, updateUserRequest: UpdateUserRequest) : User
    /**
     * 사용자 삭제
     * @param id 삭제할 사용자 ID
     */
    fun deleteById(id:Long)
}