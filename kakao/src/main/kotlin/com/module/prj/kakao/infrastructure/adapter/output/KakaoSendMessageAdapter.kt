package com.module.prj.kakao.infrastructure.adapter.output

import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.kakao.application.port.output.KakaoSendMessagePort
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class KakaoSendMessageAdapter : KakaoSendMessagePort {

    override fun send(kakaoMessage: KakaoMessage) {

        // 의 메시지 전송 오류 발생 -> 테스트시 사용
        val lastDigit = kakaoMessage.phone.takeLast(1).toIntOrNull()
        if (lastDigit != null && lastDigit % 2 == 0) {
            throw RuntimeException("임의로 발생시킨 예외 (짝수 번호)")
        }

        println("✅ Kakao 메시지 전송 성공 : $kakaoMessage")
    }
}