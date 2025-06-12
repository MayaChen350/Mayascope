package io.github.mayachen350.mayascope.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.github.mayachen350.mayascope.R
import java.time.LocalDateTime
import kotlin.collections.filter
import kotlin.random.Random
import kotlin.text.trim

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String) {
    fun formatPoemLineNumber(): String = "— Maya ${poemNumber}:${lineNumber}"
}

val RECORDED_DAY = intPreferencesKey("recorded_day")
val LINE_OF_TODAY = stringPreferencesKey("line_of_today")
val LINE_AND_POEM_NUMBER = stringPreferencesKey("line_and_poem_number")

val TOTAL_DAYS_USING_THE_MAYASCOPE = intPreferencesKey("total_days_mayascope")

class MayascopeBackend(val context: Context) {

    suspend fun getMayascope(): TodayMayascope =
        parsePoems(context.resources.openRawResource(R.raw.poems)
            .bufferedReader().use { it.readText() }
        ).let { poems: List<String> ->
            val poemIndex: Int = Random.nextInt(poems.count())
            val parsedPoemLines: List<String> = parseOnePoem(poems[poemIndex])
            val lineIndex: Int = Random.nextInt(parsedPoemLines.count())

            TodayMayascope(poemIndex + 1, lineIndex + 1, parsedPoemLines[lineIndex])
        }.also {
            saveDailyData(it)
        }

    private suspend fun saveDailyData(mayascope: TodayMayascope): Unit {
        context.dataStore.edit {
            it[RECORDED_DAY] = LocalDateTime.now().dayOfYear
            it[LINE_OF_TODAY] = mayascope.line
            it[LINE_AND_POEM_NUMBER] = mayascope.formatPoemLineNumber()
            it[TOTAL_DAYS_USING_THE_MAYASCOPE] = it[TOTAL_DAYS_USING_THE_MAYASCOPE]?.plus(1) ?: 1
        }
    }

    private fun parsePoems(poems: String): List<String> = poems.split("///")

    private fun parseOnePoem(poem: String): List<String> = poem.lines()
        .filter { it.trim() != "" }
}