package com.spring.module.auth.domain.model.event

import com.module.prj.core.domain.kakao.KakaoMessage

data class KakaoMessageEvent(
    val kakaoMessageList: List<KakaoMessage>
)
