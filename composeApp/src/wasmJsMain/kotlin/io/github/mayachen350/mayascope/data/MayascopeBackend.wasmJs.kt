package io.github.mayachen350.mayascope.data

import kotlinx.browser.document
import kotlinx.datetime.TimeZone
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

    // TODO: I realize how dumb those save fields are
    return "${
        rightNow.toLocalDateTime(
            TimeZone.currentSystemDefault()
        ).dayOfYear
    },$line,${formatPoemLineNumber()};expires=${rightNow.plus(1.days)}"
}

fun saveDailyData(mayascope: TodayMayascope) {
    document.cookie = mayascope.getCookie()
}