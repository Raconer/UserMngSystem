package com.module.prj.sms.infrastructure.adapter.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineConfig (
    @Value("\${coroutine.thread.cnt}") private val coroutineThreadCnt: Int,
){
    /**
     * 제한된 병렬성의 IO 디스패처를 생성하여 Bean 등록
     * @return CoroutineDispatcher - 제한된 병렬성 IO 디스패처
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Bean
    fun limitedIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO.limitedParallelism(this.coroutineThreadCnt)
    }
}