package com.module.prj.core.domain.kakao

import com.module.prj.core.domain.message.Message

data class KakaoMessage(
    override val phone: String,
    override val message: String) : Message
