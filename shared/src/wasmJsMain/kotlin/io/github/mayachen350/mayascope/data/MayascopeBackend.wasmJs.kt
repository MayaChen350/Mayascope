package io.github.mayachen350.mayascope.data

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

/**
 * Cookie Separator
 *
 * If I ever write "UwU" in a poem please ask me to delete it. Thank you.
 */
internal const val cs: String = "UwU"

/**
 * Order: lastPoemLine, lastLinePoemNumber
 */
@OptIn(ExperimentalTime::class)
fun TodayMayascope.getCookie(): String {
    val rightNow = Clock.System.now()

    val formatedPoemLineAndNumber = formatPoemLineNumber()

    val expirationThing: String = run {
        val tomorrow = rightNow.plus(1.days).toLocalDateTime(TimeZone.currentSystemDefault())

        fun String.formatDateNameForCookie(): String =
            this.take(3).lowercase().capitalize(Locale.current)

        fun Int.formatDateNum(): String = if (this >= 10) this.toString() else "0$this"

        val formatedDayOfWeek = tomorrow.dayOfWeek.name.formatDateNameForCookie()
        val formatedDayOfMonth = tomorrow.day.formatDateNum()
        val formatedMonthName = tomorrow.month.name.formatDateNameForCookie()
        val formattedMidnightUTCButInYourTimezone = run {
            val midnightInYourTimezone = LocalDateTime(
                1994, 2, 8, 0, 0
            ).toInstant(TimeZone.currentSystemDefault())

            val yourMidnightInUtc = midnightInYourTimezone.toLocalDateTime(TimeZone.UTC).hour

            yourMidnightInUtc.formatDateNum()
        }

        "$formatedDayOfWeek, $formatedDayOfMonth $formatedMonthName ${tomorrow.year} $formattedMidnightUTCButInYourTimezone:00:00 UTC;"
    }

    return "mayascope=$line$cs$formatedPoemLineAndNumber; expires=$expirationThing"
}