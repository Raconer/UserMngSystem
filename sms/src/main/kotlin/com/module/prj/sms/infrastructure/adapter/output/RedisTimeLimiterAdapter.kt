package com.module.prj.sms.infrastructure.adapter.output

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.sms.application.port.output.RedisTimeLimiterPort
import com.module.prj.sms.application.port.output.SmsSendMessagePort
import com.module.prj.sms.domain.model.SmsMessage
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class RedisTimeLimiterAdapter(
    private val redisTemplate: StringRedisTemplate,
    private val smsSendMessagePort: SmsSendMessagePort,
    @Value("\${sms.send.count}") private val smsSendCnt: Int,
    @Value("\${sms.send.redis-key}") private val smsSendKey: String,
    @Qualifier("limitedIoDispatcher") private val limitedDispatcher: CoroutineDispatcher
) : RedisTimeLimiterPort {

    private val objectMapper = jacksonObjectMapper()

    // 카카오톡 메시지 Redis 저장
    override fun enqueueKakaoMessageToRedisQueue(smsMessage: SmsMessage) {
        val json = this.objectMapper.writeValueAsString(smsMessage)
        try{
            this.redisTemplate.opsForList().leftPush(smsSendKey, json)
        } catch (e: Exception) {
            println("Redis push failed: ${e.message}")
        }
    }

    @Scheduled(cron = "0 * * * * *") // 매 분 0초에 실행
    override fun sendMessages() = runBlocking {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        println("[$currentTime] SMS Send Message Start =============================================")
        val ops = redisTemplate.opsForList()

        val size = ops.size(smsSendKey) ?: 0
        // 메시지가 하나라도 있을때
        if (0L < size ){
            // 500 개의 메시지를 가져온다.
            val count = minOf(smsSendCnt, size.toInt())
            println("Sending Sms Message Cnt : $count")
            val messages = ops.range(smsSendKey, -count.toLong(), -1) ?: return@runBlocking
            redisTemplate.opsForList().trim(smsSendKey, 0, -(count + 1).toLong()) // 삭제

            supervisorScope {
                    launch(limitedDispatcher){
                        println("thread: ${Thread.currentThread().name}")
                        messages.forEach { json ->
                            val smsMessage = objectMapper.readValue(json, SmsMessage::class.java)
                            smsSendMessagePort.send(smsMessage)
                        }
                    }
            }
        }
        println("[$currentTime] SMS Send Message END!! =============================================")
    }


}