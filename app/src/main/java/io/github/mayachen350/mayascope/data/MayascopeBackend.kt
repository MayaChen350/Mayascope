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

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String)
{
    fun formatPoemLineNumber(): String =  "— Maya ${poemNumber}:${lineNumber}"
}

val RECORDED_DAY = intPreferencesKey("recorded_day")
val LINE_OF_TODAY = stringPreferencesKey("line_of_today")
val LINE_AND_POEM_NUMBER = stringPreferencesKey("line_and_poem_number")

class MayascopeBackend(val context: Context) {

    public suspend fun getMayascope(): TodayMayascope =
        parsePoems(context.resources.openRawResource(R.raw.poems).bufferedReader().run {
            val poems = readText()
            close()
            poems
        }).let {
            val poemNumber: Int = Random.nextInt(it.count())
            val parsedPoem: List<String> = parseOnePoem(it[poemNumber])
            val lineNumber: Int = Random.nextInt(parsedPoem.count())

            TodayMayascope(poemNumber + 1, lineNumber +1, parsedPoem[lineNumber])
        }.also {
            saveDailyData(it)
        }

    private suspend fun saveDailyData(mayascope: TodayMayascope): Unit {
        context.dataStore.edit {
            it[RECORDED_DAY] = LocalDateTime.now().dayOfYear
            it[LINE_OF_TODAY] = mayascope.line
            it[LINE_AND_POEM_NUMBER] = mayascope.formatPoemLineNumber()
        }
    }

    private fun parsePoems(poems: String): List<String> = poems.split("///")

    private fun parseOnePoem(poem: String): List<String> =
        poem.lines().filter { it.trim() != "" }
}