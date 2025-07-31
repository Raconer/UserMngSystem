package com.module.prj.kakao.application.port.input

import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest

interface SendKakaoMessageUseCase {
    /**
     * 카카오 메시지 전송 요청 처리
     * @param kakaoSendRequest 클라이언트에서 전달받은 메시지 전송 요청 DTO
     */
    fun send(kakaoSendRequest: KakaoSendRequest)
}