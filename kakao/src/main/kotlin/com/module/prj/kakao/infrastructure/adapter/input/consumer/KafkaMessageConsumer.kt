package com.module.prj.kakao.infrastructure.adapter.input.consumer

import com.module.prj.kakao.domain.model.KakaoMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class KafkaMessageConsumer {

    private var msgReqCntPerMinute = 0
    private var lastResetTime = System.currentTimeMillis()
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @KafkaListener(
        topics = ["\${kafka.topic.kakao}"],
        groupId = "kakao-group"
    )
    fun consume(message: KakaoMessage, acknowledgment: Acknowledgment) {
        val now = System.currentTimeMillis()

        val currentMinute = LocalDateTime.now().format(timeFormatter)
        // 1분(60,000ms) 지났으면 카운터 리셋
        if (now - lastResetTime >= 60_000) {
            println("⏱️ [$currentMinute] 1분간 수신된 메시지 수: $msgReqCntPerMinute")
            msgReqCntPerMinute = 0
            lastResetTime = now
        }

        msgReqCntPerMinute++
        println("✅ [$currentMinute] 수신 메시지[$msgReqCntPerMinute]: $message")
        acknowledgment.acknowledge()
    }

}