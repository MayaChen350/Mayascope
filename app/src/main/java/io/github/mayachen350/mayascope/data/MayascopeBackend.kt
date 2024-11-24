package io.github.mayachen350.mayascope.data

import android.content.Context
import io.github.mayachen350.mayascope.R
import kotlin.collections.filter
import kotlin.random.Random
import kotlin.text.trim

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String)

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
            saveDailyData()
        }

    private suspend fun saveDailyData(): Unit {
        // TODO
        println("Date should be saved now.")
    }

    private fun parsePoems(poems: String): List<String> = poems.split("///")

    private fun parseOnePoem(poem: String): List<String> =
        poem.lines().filter { it.trim() != "" }
}