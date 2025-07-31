package com.spring.module.auth.infrastructure.adapter.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class P6SpyConfig () :MessageFormattingStrategy {
    /**
     * 애플리케이션 시작 시 P6Spy의 로그 포맷 클래스를 현재 클래스(P6SpyConfig)로 설정
     */
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
    }

    /**
     * SQL 유형에 따라 다른 포맷터 적용
     * - DDL(create, alter, comment 등)은 DDL 포맷 스타일 적용
     * - 그 외(DML 등)는 기본 포맷 스타일 적용
     */
    private fun formatSql(sql: String): String {
        val trimmedSQL = sql.trim().lowercase(Locale.ROOT)
        return when {
            trimmedSQL.startsWith("create") || trimmedSQL.startsWith("alter") || trimmedSQL.startsWith("comment") ->
                FormatStyle.DDL.formatter.format(sql)
            else ->
                FormatStyle.BASIC.formatter.format(sql)
        }
    }

    override fun formatMessage( connectionId: Int,
                                now: String?,
                                elapsed: Long,
                                category: String?,
                                prepared: String?,
                                sql: String,
                                url: String?): String {
        var formattedSql = sql
        // SQL이 비어있지 않고 카테고리가 STATEMENT인 경우 포맷 적용
        if (category == Category.STATEMENT.name && sql.trim().isNotEmpty()) {
            formattedSql = formatSql(sql)
        }
        // 최종 로그 출력 형식: [카테고리] | 실행시간 | 포맷된 SQL
        return String.format("[%s] | %d ms | %s", category, elapsed, formattedSql)
    }
}