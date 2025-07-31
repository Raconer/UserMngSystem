package com.module.prj.kakao.infrastructure.adapter.input.event

import com.module.prj.core.common.GlobalConstants
import com.module.prj.kakao.domain.model.event.SmsMessageEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class SmsMessageEventListener (
    private val restTemplate: RestTemplate,
    @Value("\${external.sms.url}")
    private val smsUrl: String,
    @Value("\${external.sms.username}")
    private val username: String,
    @Value("\${external.sms.password}")
    private val password: String
){
    @Async
    @EventListener
    fun send(event: SmsMessageEvent) {
        println("📨 SMS 대체 전송: ${event.smsMessageList.size}건")
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
                println("실패: ${ex.message}")
                throw ex
            }
        }
    }
}