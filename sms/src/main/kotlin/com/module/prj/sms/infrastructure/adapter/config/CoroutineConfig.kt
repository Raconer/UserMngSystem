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
    @OptIn(ExperimentalCoroutinesApi::class)
    @Bean
    fun limitedIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO.limitedParallelism(this.coroutineThreadCnt)
    }
}