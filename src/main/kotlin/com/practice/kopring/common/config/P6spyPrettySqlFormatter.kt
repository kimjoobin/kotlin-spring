package com.practice.kopring.common.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import java.util.Locale

class P6spyPrettySqlFormatter : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String,
        elapsed: Long,
        category: String,
        prepared: String,
        sql: String,
        url: String
    ): String {
        var sqlQuery = sql.trim()

        // SQL이 비어있으면 패스
        if (sqlQuery.isEmpty()) return ""

        // 카테고리 필터링 (Statement만 출력)
        if (Category.STATEMENT.name != category) return ""

        // SQL 포맷팅
        sqlQuery = formatSql(sqlQuery)

        return buildString {
            append("\n")
            append("=".repeat(100))
            append("\n🔍 SQL Query (Connection ID: $connectionId)")
            append("\n⏱️ Execution Time: ${elapsed}ms")
            append("\n📝 SQL:\n$sqlQuery")
            append("\n")
            append("=".repeat(100))
            append("\n")
        }
    }

    private fun formatSql(sql: String): String {
        return if (isDDL(sql)) {
            FormatStyle.DDL.formatter.format(sql)
        } else {
            FormatStyle.BASIC.formatter.format(sql)
        }
    }

    private fun isDDL(sql: String): Boolean {
        val upperSql = sql.uppercase(Locale.getDefault()).trim()
        return upperSql.startsWith("CREATE") ||
                upperSql.startsWith("ALTER") ||
                upperSql.startsWith("DROP") ||
                upperSql.startsWith("TRUNCATE")
    }
}