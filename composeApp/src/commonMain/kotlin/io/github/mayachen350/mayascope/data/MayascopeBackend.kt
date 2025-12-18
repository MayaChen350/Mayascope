package io.github.mayachen350.mayascope.data

import mayascope.composeapp.generated.resources.Res
import kotlin.random.Random

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String) {
    fun formatPoemLineNumber(): String = "— Maya ${poemNumber}:${lineNumber}"
}

object MayascopeBackend {
    suspend fun getNewMayascope(): TodayMayascope =
        parsePoems(
            Res.readBytes("files/poems.txt").decodeToString()
        )
            .let { poems: List<String> ->
                val poemIndex: Int = Random.nextInt(poems.count())
                val parsedPoemLines: List<String> = parseOnePoem(poems[poemIndex])
                val lineIndex: Int = Random.nextInt(parsedPoemLines.count())

                TodayMayascope(
                    // The easter egg is considered "poem 0"
                    if (poemIndex == poems.lastIndex) 0 else poemIndex + 1,
                    lineIndex + 1,
                    parsedPoemLines[lineIndex]
                )
            }

    private fun parsePoems(poems: String): List<String> = poems.split("///")
        .toMutableList().also {
            // Maya here
            //    Ahem. I just realized I put this as a joke a WHIIILE ago (when I made the program)
            //    Though it actually "unchalice" the whole numbering system (eesh)
            //    So I removed it from the poem list, BUT let it in the list still as "poem 0"
            //    Teehee. Don't be jumpscared!
            it.add(",>,[<->-]<>>+++[<++++>-]<[<++++>-]<.")
        }

    private fun parseOnePoem(poem: String): List<String> = poem.lines()
        .map { it.trim() }
        .filter { it != "" }
        .map { it.trim('"').trim() }
}

//typealias SaveDataFunc = suspend (TodayMayascope) -> Unit