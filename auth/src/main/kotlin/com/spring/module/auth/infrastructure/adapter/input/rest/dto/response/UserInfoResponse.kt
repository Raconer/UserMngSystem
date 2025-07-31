package com.spring.module.auth.infrastructure.adapter.input.rest.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "사용자 정보 응답")
data class UserInfoResponse(
    @Schema(description = "사용자 아이디", example = "testuser")
    val username:String,
    @Schema(description = "비밀번호 (암호화됨)", example = "$2a$10$...")
    var password:String,
    @Schema(description = "이름", example = "홍길동")
    val name:String,
    @Schema(description = "주민등록번호", example = "9001011234567")
    val rrn:String,
    @Schema(description = "전화번호", example = "010-1234-5678")
    val phoneNumber:String,
    @Schema(description = "주소", example = "서울시 강남구")
    val address:String,
    @Schema(description = "계정 생성일시", example = "2025-07-31T10:15:30")
    val createdAt: LocalDateTime? = null,
    @Schema(description = "계정 수정일시", example = "2025-07-31T11:00:00")
    val updatedAt: LocalDateTime? = null
)