package com.module.prj.kakao.application.port.output

import com.module.prj.core.domain.kakao.KakaoMessage

interface KakaoSendMessagePort {
    /**
     * 카카오 메시지 전송 실행
     * @param kakaoMessage 전송할 카카오 메시지 도메인 객체
     */
    fun send(kakaoMessage: KakaoMessage)
}