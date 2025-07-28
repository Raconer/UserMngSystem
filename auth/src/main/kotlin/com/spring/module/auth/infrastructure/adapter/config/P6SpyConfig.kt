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
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
    }

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
        if (category == Category.STATEMENT.name && sql.trim().isNotEmpty()) {
            formattedSql = formatSql(sql)
        }
        return String.format("[%s] | %d ms | %s", category, elapsed, formattedSql)
    }
}