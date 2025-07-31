package com.spring.module.auth.application.service

import com.module.prj.core.domain.kakao.KakaoMessage
import com.spring.module.auth.application.port.input.SendKakaoMessageUseCase
import com.spring.module.auth.application.port.output.UserRepositoryPort
import com.spring.module.auth.domain.model.event.KakaoMessageEvent
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.request.SendKakaoMessageRequest
import com.module.prj.core.common.GlobalConstants
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

/**
 * 연령대별 사용자에게 카카오톡 메시지를 전송하는 서비스
 */
@Service
class SendKakaoMessageService(
    private val userRepositoryPort: UserRepositoryPort,
    private val eventPublisher: ApplicationEventPublisher
): SendKakaoMessageUseCase {
    /**
     * 연령대 기준으로 사용자 목록을 조회하고 카카오 메시지 이벤트 발행
     */
    override fun sendKakaoToAgeGroup( request: SendKakaoMessageRequest) {
        val kakaoMessageList = this.userRepositoryPort.findByAgeGroup(request.ageGroup!!).map {
            KakaoMessage(
                phone = it.phoneNumber,
                message = String.format(GlobalConstants.SEND_KAKAO_MESSAGE, it.name)
            )
        }

        val kakaoMessageEvent = KakaoMessageEvent(kakaoMessageList)

        this.eventPublisher.publishEvent(kakaoMessageEvent)
    }
}