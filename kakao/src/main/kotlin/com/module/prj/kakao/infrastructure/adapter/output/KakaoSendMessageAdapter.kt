package com.module.prj.kakao.infrastructure.adapter.output

import com.module.prj.kakao.application.port.output.KakaoSendMessagePort
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import com.module.prj.kakao.domain.model.KakaoMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class KakaoSendMessageAdapter : KakaoSendMessagePort {

    override fun send(kakaoMessage: KakaoMessage) {
        try {
            println("Kakao 메시지 전송 성공 : $kakaoMessage}")
        } catch (e: Exception) {
            println("❌ 전송 실패 → SMS 전송")
        }
    }
}