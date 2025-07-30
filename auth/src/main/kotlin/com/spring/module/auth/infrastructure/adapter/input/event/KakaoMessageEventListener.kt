package com.spring.module.auth.infrastructure.adapter.input.event

import com.spring.module.auth.domain.model.KakaoMessage
import com.spring.module.auth.domain.model.event.KakaoMessageEvent
import com.spring.module.auth.infrastructure.rest.constant.GlobalConstants
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
class KakaoMessageEventListener(
    private val restTemplate: RestTemplate,
    @Value("\${external.kakao.url}")
    private val kakaoUrl: String,
    @Value("\${external.kakao.username}")
    private val username: String,
    @Value("\${external.kakao.password}")
    private val password: String
)  {

    @Async
    @EventListener
    fun send(event: KakaoMessageEvent) {
        val encoded = Base64.getEncoder().encodeToString("$username:$password".toByteArray())

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Authorization", "Basic $encoded")
        }

        event.kakaoMessageList.forEach { kakaoMessage ->

            val request = HttpEntity(kakaoMessage, headers)

            try {
                restTemplate.postForEntity(kakaoUrl, request, String::class.java)
            } catch (ex: Exception) {
                println("실패: ${ex.message}")
                throw ex
            }
        }
    }
}