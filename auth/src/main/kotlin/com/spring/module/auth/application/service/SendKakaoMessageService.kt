package com.spring.module.auth.application.service

import com.spring.module.auth.application.port.input.SendKakaoMessageUseCase
import com.spring.module.auth.application.port.output.KakaoMessageSendPort
import com.spring.module.auth.application.port.output.UserRepositoryPort
import com.spring.module.auth.domain.model.KakaoMessage
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SendKakaoMessageRequest
import com.spring.module.auth.infrastructure.rest.constant.GlobalConstants
import org.springframework.stereotype.Service

@Service
class SendKakaoMessageService(
    private val userRepositoryPort: UserRepositoryPort,
    private val kakaoMessageSendPort: KakaoMessageSendPort
): SendKakaoMessageUseCase {
    override fun sendKakaoToAgeGroup( request: SendKakaoMessageRequest) {
        this.userRepositoryPort.findByAgeGroup(request.ageGroup!!).forEach {
            val message = KakaoMessage(
                phone = it.phoneNumber,
                message = String.format(GlobalConstants.SEND_KAKAO_MESSAGE, it.name)
            )

            this.kakaoMessageSendPort.send(message)
        }
    }
}