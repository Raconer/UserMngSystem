package com.module.prj.kakao.application.port.input

import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest

interface SendKakaoMessageUseCase {
    fun send(kakaoSendRequest: KakaoSendRequest)
}