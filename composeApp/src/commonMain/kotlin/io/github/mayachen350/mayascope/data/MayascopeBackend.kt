package io.github.mayachen350.mayascope.data

import mayascope.composeapp.generated.resources.Res
import kotlin.random.Random

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String) {
    fun formatPoemLineNumber(): String = "— Maya ${poemNumber}:${lineNumber}"
}

class MayascopeBackend() {

    suspend fun getNewMayascope(): TodayMayascope =
        parsePoems(
            Res.readBytes("files/poems.txt").decodeToString()
        )
            .let { poems: List<String> ->
                val poemIndex: Int = Random.nextInt(poems.count())
                val parsedPoemLines: List<String> = parseOnePoem(poems[poemIndex])
                val lineIndex: Int = Random.nextInt(parsedPoemLines.count())

                TodayMayascope(poemIndex + 1, lineIndex + 1, parsedPoemLines[lineIndex])
            }

    private fun parsePoems(poems: String): List<String> = poems.split("///")

    private fun parseOnePoem(poem: String): List<String> = poem.lines()
        .map { it.trim() }
        .filter { it != "" }
        .map { it.trim('"').trim() }
}