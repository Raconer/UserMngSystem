package com.module.prj.kakao.infrastructure.adapter.output

import com.module.prj.kakao.application.port.out.KafkaMessageProducerPort
import com.module.prj.kakao.domain.model.KakaoMessage
import com.module.prj.kakao.infrastructure.adapter.input.rest.dto.request.KakaoSendRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class KafkaMessageProducerAdapter (
    private val kafkaTemplate: KafkaTemplate<String, KakaoMessage>,
    @Value("\${kafka.topic.kakao}")
    private val topic: String
): KafkaMessageProducerPort{
    private var msgReqCntPerMinute = 0
    private var lastResetTime = System.currentTimeMillis()
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")


    override fun send(kakaoSendRequest: KakaoSendRequest) {
        val now = System.currentTimeMillis()

        val currentMinute = LocalDateTime.now().format(timeFormatter)
        // 1분 경과 시 카운터 리셋
        if (now - lastResetTime >= 60_000) {
            println("⏱\uFE0F [$currentMinute] 1분간 전송된 메시지 수: $msgReqCntPerMinute")
            msgReqCntPerMinute = 0
            lastResetTime = now
        }

        val message = KakaoMessage(
            phone = kakaoSendRequest.phone!!,
            message = kakaoSendRequest.message!!
        )

        val future = this.kafkaTemplate.send(topic, message)
        future.whenComplete { result, ex ->
            if (ex != null) {
                println("❌ [$currentMinute] 전송 실패: ${ex.message}")
            } else {
                msgReqCntPerMinute++
                println("\uD83D\uDCE4 [$currentMinute] 전송 성공[$msgReqCntPerMinute]: ${result?.producerRecord}")
            }
        }
    }
}