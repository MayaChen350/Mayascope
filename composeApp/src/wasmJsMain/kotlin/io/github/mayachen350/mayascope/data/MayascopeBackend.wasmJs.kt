package io.github.mayachen350.mayascope.data

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import kotlinx.browser.document
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

/**
 * Order: lastRecordDay, lastPoemLine, lastLinePoemNumber
 */
@OptIn(ExperimentalTime::class)
fun TodayMayascope.getCookie(): String {
    val rightNow = Clock.System.now()

    // TODO: I realize how dumb those saved fields are
    return "mayascope=${
        rightNow.toLocalDateTime(
            TimeZone.currentSystemDefault()
        ).dayOfYear
    },$line,${formatPoemLineNumber()}; expires=${
        rightNow.plus(1.days).toLocalDateTime(TimeZone.currentSystemDefault()).run {
            "${
                dayOfWeek.name.take(3).lowercase().capitalize(Locale.current)
            }, ${day.run { if (this >= 10) this.toString() else "0$this" }} ${
                month.name.take(3).lowercase().capitalize(Locale.current)
            } $year ${
                LocalDateTime(
                    1994, 2, 8, 0, 0,
                    0, 0
                ).toInstant(TimeZone.currentSystemDefault())
                    .toLocalDateTime(TimeZone.UTC).hour.run {
                        if (this >= 10) this.toString() else "0$this"
                    }
            }:00:00 UTC;"
        }
    }"
}

fun saveDailyData(mayascope: TodayMayascope) {
    document.cookie = mayascope.getCookie()
}