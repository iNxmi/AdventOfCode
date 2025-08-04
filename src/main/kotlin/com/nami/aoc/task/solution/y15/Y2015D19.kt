package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D19 : Task<List<String>>(2015, 19) {

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>>(
        this, Suffix.A
    ) {
        override fun solve(input: List<String>) = null
    }

    override fun getPartB() = object : Part<List<String>>(
        this, Suffix.B
    ) {
        override fun solve(input: List<String>) = null
    }

}

fun main() = Y2015D19().getTestVerifications().print()
//fun main() = Y2015D19().getResults().print()
//fun main() = Y2015D19().getVerifications().print()