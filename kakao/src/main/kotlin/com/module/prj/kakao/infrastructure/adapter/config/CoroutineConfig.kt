package com.module.prj.kakao.infrastructure.adapter.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Coroutine Dispatcher 설정 클래스
 * - 지정된 쓰레드 수만큼 병렬 처리 가능한 제한된 IO Dispatcher 빈을 등록
 */
@Configuration
class CoroutineConfig (
    @Value("\${coroutine.thread.cnt}") private val coroutineThreadCnt: Int,
){
    /**
     * 제한된 병렬성의 IO Dispatcher Bean 등록
     * - ex) coroutine.thread.cnt: 8 → 동시에 8개의 코루틴만 실행
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Bean
    fun limitedIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO.limitedParallelism(this.coroutineThreadCnt)
    }
}