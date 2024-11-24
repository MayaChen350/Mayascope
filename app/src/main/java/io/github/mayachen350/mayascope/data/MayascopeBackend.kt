package io.github.mayachen350.mayascope.data

import kotlin.collections.filter
import kotlin.random.Random
import kotlin.text.trim

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String)

class MayascopeBackend(val poems: String) {

    public suspend fun getMayascope(): TodayMayascope =
        parsePoems(poems)
            .let {
                val poemNumber: Int = Random.nextInt(it.count())
                val parsedPoem: List<String> = parseOnePoem(it[poemNumber])
                val lineNumber: Int = Random.nextInt(parsedPoem.count())

                TodayMayascope(poemNumber, lineNumber, parsedPoem[lineNumber])
            }.also {
                saveDailyData()
            }

    private suspend fun saveDailyData(): Unit {
        // TODO
        println("Date should be saved now.")
    }

    private fun parsePoems(poems: String): List<String> = poems.split("///")

    private fun parseOnePoem(poem: String): List<String> = poem.lines().filter { it.trim() != "" }
}