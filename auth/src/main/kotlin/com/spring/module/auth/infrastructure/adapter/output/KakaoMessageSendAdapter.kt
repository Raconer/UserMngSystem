package com.spring.module.auth.infrastructure.adapter.output

import com.spring.module.auth.application.port.out.KakaoMessageSendPort
import com.spring.module.auth.domain.model.KakaoMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.Base64

@Component
class KakaoMessageSendAdapter(
    private val restTemplate: RestTemplate,
    @Value("\${external.kakao.url}")
    private val kakaoUrl: String,
    @Value("\${external.kakao.username}")
    private val username: String,
    @Value("\${external.kakao.password}")
    private val password: String
) : KakaoMessageSendPort {
    override fun send(message: KakaoMessage) {
        val encoded = Base64.getEncoder().encodeToString("$username:$password".toByteArray())

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Authorization", "Basic $encoded")
        }

        val request = HttpEntity(message, headers)

        // ✅ 바디 및 헤더 확인 로그
        println("보낼 메시지: $message")
        println("헤더: $headers")

        try {
            val response = restTemplate.postForEntity(kakaoUrl, request, String::class.java)
            println("응답 코드: ${response.statusCode}")
        } catch (ex: Exception) {
            println("실패: ${ex.message}")
            throw ex
        }
    }
}