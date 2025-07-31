package com.spring.module.auth.infrastructure.adapter.input.event

import com.module.prj.core.common.GlobalConstants
import com.spring.module.auth.domain.model.event.KakaoMessageEvent
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

@Component
class KakaoMessageEventListener(
    private val restTemplate: RestTemplate,
    // 외부 카카오 API URL (예: http://localhost:8081/kakao)
    @Value("\${external.kakao.url}")
    private val kakaoUrl: String,
    // Basic Auth 사용자명
    @Value("\${external.kakao.username}")
    private val username: String,
    // Basic Auth 비밀번호
    @Value("\${external.kakao.password}")
    private val password: String
)  {

    private val log = LoggerFactory.getLogger(KakaoMessageEventListener::class.java)

    /**
     * KakaoMessageEvent 발생 시 호출되는 메서드
     * - Base64 인코딩된 Basic 인증 헤더 생성
     * - 메시지 리스트를 병렬 스트림으로 반복하며 외부 API 호출
     * - 실패 시 예외 출력 및 전파
     */
    @Async
    @EventListener
    fun send(event: KakaoMessageEvent) {
        // Basic 인증용 Base64 인코딩
        val encoded = Base64.getEncoder().encodeToString("$username:$password".toByteArray())

        // 요청 헤더 설정
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set(GlobalConstants.AUTHORIZATION_HEADER, "Basic $encoded")
        }

        // 메시지 병렬 전송
        event.kakaoMessageList.stream().parallel().forEach { kakaoMessage ->
            val request = HttpEntity(kakaoMessage, headers)

            try {
                restTemplate.postForEntity(kakaoUrl, request, String::class.java)
            } catch (ex: Exception) {
                log.error("실패: ${ex.message}")
                throw ex
            }
        }
    }
}