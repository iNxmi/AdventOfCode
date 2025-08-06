package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D19 : Task<Y2015D19.Input>(2015, 19) {

    data class Replacement(val base: String, val replacement: String)
    data class Input(val replacements: Int, val molecule: String)

    override fun getProcessedInput(raw: String): Input {
        val split = raw.split("\n\n")
        val replacements = split[0].lines()
        val molecule = split[1]
        return Input(0, "")
    }

    override fun getPartA() = object : Part<Input>(
        this, Suffix.A
    ) {
        override fun solve(input: Input) = null
    }

    override fun getPartB() = object : Part<Input>(
        this, Suffix.B
    ) {
        override fun solve(input: Input) = null
    }

}

fun main() = Y2015D19().getTestVerifications().print()
//fun main() = Y2015D19().getResults().print()
//fun main() = Y2015D19().getVerifications().print()