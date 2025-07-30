package com.spring.module.auth.domain.model.event

import com.spring.module.auth.domain.model.KakaoMessage

data class KakaoMessageEvent(
    val kakaoMessageList: List<KakaoMessage>
)
