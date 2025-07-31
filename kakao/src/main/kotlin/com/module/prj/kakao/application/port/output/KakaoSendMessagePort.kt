package com.module.prj.kakao.application.port.output

import com.module.prj.core.domain.kakao.KakaoMessage

interface KakaoSendMessagePort {
    fun send(kakaoMessage: KakaoMessage)
}