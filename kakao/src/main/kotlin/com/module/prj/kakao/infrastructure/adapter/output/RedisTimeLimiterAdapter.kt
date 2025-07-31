package com.module.prj.kakao.infrastructure.adapter.output

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.kakao.application.port.output.KakaoSendMessagePort
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
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
    private val kakaoSendMessagePort: KakaoSendMessagePort,
    @Value("\${kakao.send.token}")
    private val kakaoSendTokenList: List<String>,
    @Value("\${kakao.send.count}") private val kakaoSendCnt: Int,
    @Value("\${kakao.send.redis-key}") private val kakaoSendKey: String,
    @Qualifier("limitedIoDispatcher") private val limitedDispatcher: CoroutineDispatcher
) : RedisTimeLimiterPort {

    private val objectMapper = jacksonObjectMapper()

    // 카카오톡 메시지 Redis 저장
    override fun enqueueKakaoMessageToRedisQueue(kakaoMessage: KakaoMessage) {
        val json = this.objectMapper.writeValueAsString(kakaoMessage)
        try{
            this.redisTemplate.opsForList().leftPush(kakaoSendKey, json)
        } catch (e: Exception) {
            println("Redis push failed: ${e.message}")
        }
    }

    @Scheduled(cron = "0 * * * * *") // 매 분 0초에 실행
    override fun sendMessages() = runBlocking {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        println("[$currentTime] Kakao Send Message Start =============================================")
        val ops = redisTemplate.opsForList()

        val size = ops.size(kakaoSendKey) ?: 0
        // 메시지가 하나라도 있을때
        if (0L < size ){
            val tokenSize =  kakaoSendTokenList.size
            val sendKakaoMessageSize = kakaoSendCnt * tokenSize
            // 토큰 * 100 개의 메시지를 가져온다.
            val count = minOf(sendKakaoMessageSize, size.toInt())
            println("[$tokenSize] Sending Kakao Message Cnt : $count")
            val messages = ops.range(kakaoSendKey, -count.toLong(), -1) ?: return@runBlocking
            redisTemplate.opsForList().trim(kakaoSendKey, 0, -(count + 1).toLong()) // 삭제

            val messageList = divideListEqually(messages, tokenSize)

            supervisorScope {
                kakaoSendTokenList.zip(messageList).forEach { (token, msgChunk) ->


                    launch(limitedDispatcher){
                        println("[${msgChunk.size}]Launching for token: $token on thread: ${Thread.currentThread().name}")
                        msgChunk.forEach { json ->
                            val kakaoSendMessage = objectMapper.readValue(json, KakaoMessage::class.java)
                            kakaoSendMessagePort.send(kakaoSendMessage)
                        }
                    }
                }
            }
        }
        println("[$currentTime] Kakao Send Message END!! =============================================")
    }

    private fun divideListEqually(list: List<String>, n: Int): List<List<String>> {
        val size = list.size
        return List(n) { i ->
            val start = (i * size) / n
            val end = ((i + 1) * size) / n
            list.subList(start, end)
        }
    }
}