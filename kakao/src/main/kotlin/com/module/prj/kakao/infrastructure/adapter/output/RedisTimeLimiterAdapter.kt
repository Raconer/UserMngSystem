package com.module.prj.kakao.infrastructure.adapter.output

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.module.prj.kakao.application.port.output.KakaoSendMessagePort
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import com.module.prj.kakao.domain.model.KakaoMessage
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class RedisTimeLimiterAdapter(
    private val redisTemplate: StringRedisTemplate,
    private val kakaoSendMessagePort: KakaoSendMessagePort
) : RedisTimeLimiterPort {

    private val objectMapper = jacksonObjectMapper()


    // 카카오톡 메시지 Redis 저장
    override fun enqueueKakaoMessageToRedisQueue(message: KakaoMessage) {
        val json = objectMapper.writeValueAsString(message)
        redisTemplate.opsForList().leftPush("kakao:queue", json)
    }

    @Scheduled(cron = "0 * * * * *") // 매 분 0초에 실행
    override fun sendMessages() {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        println("[$currentTime] Kakao Send Message Start =============================================")
        val key = "kakao:queue"
        val ops = redisTemplate.opsForList()

        val size = ops.size(key) ?: 0
        if (0L < size ){
            val count = if (size >= 100) 100 else size.toInt()

            val messages = ops.range(key, -count.toLong(), -1) ?: return
            redisTemplate.opsForList().trim(key, 0, -(count + 1).toLong()) // 삭제

            messages.reversed().forEach { json ->
                val kakaoSendMessage = objectMapper.readValue(json, KakaoMessage::class.java)
                this.kakaoSendMessagePort.send(kakaoSendMessage)
            }
        }


        println("[$currentTime] Kakao Send Message END!! =============================================")
    }


}