package com.module.prj.kakao.infrastructure.adapter.output

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.module.prj.core.domain.kakao.KakaoMessage
import com.module.prj.core.domain.sms.SmsMessage
import com.module.prj.kakao.application.port.output.KakaoSendMessagePort
import com.module.prj.kakao.application.port.output.RedisTimeLimiterPort
import com.module.prj.kakao.domain.model.event.SmsMessageEvent
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class RedisTimeLimiterAdapter(
    @Value("\${kakao.send.token}")
    private val kakaoSendTokenList: List<String>,                                           // 사용할 카카오 토큰 목록
    @Value("\${kakao.send.count}") private val kakaoSendCnt: Int,                           // 토큰당 분당 전송 가능 메시지 수 (ex. 100건)
    @Value("\${kakao.send.redis-key}") private val kakaoSendKey: String,                    // Redis에서 사용할 키
    @Qualifier("limitedIoDispatcher") private val limitedDispatcher: CoroutineDispatcher,   // 병렬 처리를 위한 Coroutine Dispatcher
    private val redisTemplate: StringRedisTemplate,         // Redis 템플릿
    private val kakaoSendMessagePort: KakaoSendMessagePort, // 메시지 전송 포트
    private val eventPublisher: ApplicationEventPublisher   // SMS 대체 발송 이벤트 발행용
) : RedisTimeLimiterPort {

    private val log = LoggerFactory.getLogger(KakaoSendMessageAdapter::class.java)

    private val objectMapper = jacksonObjectMapper()

    /**
     * 카카오 메시지를 Redis 큐에 저장
     */
    override fun enqueueKakaoMessageToRedisQueue(kakaoMessage: KakaoMessage) {
        val json = this.objectMapper.writeValueAsString(kakaoMessage)
        try{
            this.redisTemplate.opsForList().leftPush(kakaoSendKey, json)
        } catch (e: Exception) {
            log.warn("Redis push failed: ${e.message}")
        }
    }

    /**
     * 매 분마다 Redis에 쌓인 카카오 메시지를 분산 발송
     * - 토큰 개수 * 분당 제한 수 만큼 병렬 전송
     * - 실패 시 SmsMessageEvent로 대체 전송 처리
     */
    @Scheduled(cron = "0 * * * * *") // 매 분 0초에 실행
    override fun sendMessages() = runBlocking {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        log.info("[$currentTime] Kakao Send Message Start =============================================")
        val ops = redisTemplate.opsForList()

        val size = ops.size(kakaoSendKey) ?: 0 // Redis에 쌓인 메시지 수 확인
        // 메시지가 하나라도 있을때
        if (0L < size ){
            val tokenSize =  kakaoSendTokenList.size                // 사용 가능한 토큰 수
            val sendKakaoMessageSize = kakaoSendCnt * tokenSize     // 전체 전송 가능 수 (ex: 100 * 4 = 400)
            val count = minOf(sendKakaoMessageSize, size.toInt())   // 실제 가져올 메시지 수 (토큰 * 100 개)

            log.info("[$tokenSize] Sending Kakao Message Cnt : $count")
            val messages = ops.range(kakaoSendKey, -count.toLong(), -1) ?: return@runBlocking   // 최근 메시지 N개 가져오기
            redisTemplate.opsForList().trim(kakaoSendKey, 0, -(count + 1).toLong()) // 가져온 메시지는 Redis에서 제거

            val messageList = divideListEqually(messages, tokenSize)    // 토큰 수만큼 균등 분할 (병렬 처리용)

            supervisorScope {
                // 각 토큰과 메시지 chunk를 묶어서 병렬 처리
                val results = kakaoSendTokenList.zip(messageList).map { (token, msgChunk) ->
                    async(limitedDispatcher) {
                        msgChunk.map { json ->
                            async{
                                // 논블록킹 테스트
                                // println("[${msgChunk.size}]Launching for token: $token on thread: ${Thread.currentThread().name}")
                                // delay(10000)
                                // println("NonBlocking: ${Thread.currentThread().name}")
                                val kakaoSendMessage = withContext(Dispatchers.Default){
                                    objectMapper.readValue(json, KakaoMessage::class.java)
                                }

                                runCatching {
                                    kakaoSendMessagePort.send(kakaoSendMessage) // 📤 카카오 메시지 전송
                                }.exceptionOrNull()?.let {
                                    // ❗ 예외 발생 시 로그 및 SMS 대체용 메시지로 저장
                                    SmsMessage(
                                        phone = kakaoSendMessage.phone,
                                        message = kakaoSendMessage.message
                                    )
                                }

                            }
                        }.awaitAll().filterNotNull()
                    }
                }.awaitAll().flatten()  // 모든 async 완료 후 결과 수집

                eventPublisher.publishEvent(SmsMessageEvent(results))
            }
        }
        log.info("[$currentTime] Kakao Send Message END!! =============================================")
    }

    /**
     * 메시지 리스트를 균등하게 분할
     * @param list 전체 메시지 리스트
     * @param n 분할할 그룹 수 (토큰 수)
     */
    private fun divideListEqually(list: List<String>, n: Int): List<List<String>> {
        val size = list.size
        return List(n) { i ->
            val start = (i * size) / n
            val end = ((i + 1) * size) / n
            list.subList(start, end)
        }
    }
}