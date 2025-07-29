package com.module.prj.kakao.application.port.out

import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest

interface KafkaMessageProducerPort {
    fun send(kakaoSendRequest: KakaoSendRequest)
}