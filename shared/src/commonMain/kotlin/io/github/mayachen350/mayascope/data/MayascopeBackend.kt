package io.github.mayachen350.mayascope.data

import kotlin.random.Random

data class TodayMayascope(val poemNumber: Int, val lineNumber: Int, val line: String) {
    fun formatPoemLineNumber(): String = "— Maya ${poemNumber}:${lineNumber}"
}

object MayascopeBackend {
    fun getNewMayascope(): TodayMayascope = poemsArrays
        .let { poems ->
            val poemIndex: Int = Random.nextInt(poems.count() + 1)
            // The easter egg is considered "poem 0"
            if (poemIndex == poems.count() + 1) return@let secretMayascope

            val poemLines: Array<String> = poems[poemIndex]
            val lineIndex: Int = Random.nextInt(poemLines.count())

            TodayMayascope(
                poemIndex + 1,
                lineIndex + 1,
                poemLines[lineIndex]
            )
        }

    private val secretMayascope: TodayMayascope
        get() = run {
            // Maya here
            //    Ahem. I just realized I put this as a joke a WHIIILE ago (when I made the program)
            //    Though it actually "unchalice" the whole numbering system (eesh)
            //    So I removed it from the poem list, BUT let it in the list still as "poem 0"
            //    Teehee. Don't be jumpscared!
            TodayMayascope(0, 0, ",>,[<->-]<>>+++[<++++>-]<[<++++>-]<.")
        }
}