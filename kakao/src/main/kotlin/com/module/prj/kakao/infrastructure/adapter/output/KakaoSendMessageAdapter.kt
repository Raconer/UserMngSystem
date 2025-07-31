package com.module.prj.kakao.infrastructure.adapter.output

import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.kakao.application.port.output.KakaoSendMessagePort
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import com.module.prj.kakao.infrastructure.adapter.input.event.SmsMessageEventListener
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class KakaoSendMessageAdapter : KakaoSendMessagePort {

    private val log = LoggerFactory.getLogger(KakaoSendMessageAdapter::class.java)

    override fun send(kakaoMessage: KakaoMessage) {

        // 임의 메시지 전송 오류 발생 -> 테스트시 사용
            val lastDigit = kakaoMessage.phone.takeLast(1).toIntOrNull()
            if (lastDigit != null && lastDigit % 2 == 0) {
                throw RuntimeException("임의로 발생시킨 예외 (짝수 번호)")
            }

        log.info("✅ Kakao 메시지 전송 성공 : $kakaoMessage")
    }
}