package com.module.prj.kakao.application.service

import com.module.prj.kakao.application.port.`in`.SendKakaoMessageUseCase
import com.module.prj.kakao.application.port.out.KafkaMessageProducerPort
import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest
import org.springframework.stereotype.Service

@Service
class SendKakaoMessageService (
    private val kafkaMessageProducerPort: KafkaMessageProducerPort
) : SendKakaoMessageUseCase {

    override fun send(kakaoSendRequest: KakaoSendRequest) {
        this.kafkaMessageProducerPort.send(kakaoSendRequest)
    }

}