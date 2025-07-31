package com.spring.module.auth.domain.model.event

import com.module.prj.core.domain.kakao.KakaoMessage

data class KakaoMessageEvent(
    /**
     * 카카오 메시지 리스트
     * - 각 사용자에게 전송할 메시지 정보가 담긴 리스트
     */
    val kakaoMessageList: List<KakaoMessage>
)
