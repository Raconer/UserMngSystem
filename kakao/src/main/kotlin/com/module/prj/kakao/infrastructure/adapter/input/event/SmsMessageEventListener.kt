package com.module.prj.kakao.infrastructure.adapter.input.event

import com.module.prj.core.common.GlobalConstants
import com.module.prj.kakao.domain.model.event.SmsMessageEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

/**
 * SMS 메시지 대체 전송 이벤트 리스너
 * - 도메인 이벤트로 전달된 SmsMessageEvent를 수신하여
 *   외부 SMS API에 비동기 병렬 전송을 수행
 */
@Component
class SmsMessageEventListener (
    private val restTemplate: RestTemplate,
    @Value("\${external.sms.url}")
    private val smsUrl: String,     // 외부 SMS 전송 API URL
    @Value("\${external.sms.username}")
    private val username: String,   // 기본 인증용 사용자명
    @Value("\${external.sms.password}")
    private val password: String    // 기본 인증용 비밀번호
){
    private val log = LoggerFactory.getLogger(SmsMessageEventListener::class.java)

    /**
     * 비동기 이벤트 핸들러
     * - 수신된 SMS 메시지 리스트를 외부 API로 병렬 전송
     */
    @Async
    @EventListener
    fun send(event: SmsMessageEvent) {
        log.info("📨 SMS 대체 전송: ${event.smsMessageList.size}건")
        val encoded = Base64.getEncoder().encodeToString("$username:$password".toByteArray())

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set(GlobalConstants.AUTHORIZATION_HEADER, "Basic $encoded")
        }

        event.smsMessageList.stream().parallel().forEach { smsMessage ->
            val urlWithParam = "$smsUrl?phone=${smsMessage.phone}"

            val body = mapOf("message" to smsMessage.message)
            val request = HttpEntity(body, headers)

            try {
                restTemplate.postForEntity(urlWithParam, request, String::class.java)
            } catch (ex: Exception) {
                log.error("실패: ${ex.message}")
                throw ex
            }
        }
    }
}