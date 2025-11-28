package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Template : Task<List<String>>(2025, 1) {

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>>(
        this, Suffix.A
    ) {
        override fun solve(input: List<String>) = 0
    }

    override fun getPartB() = object : Part<List<String>>(
        this, Suffix.B
    ) {
        override fun solve(input: List<String>) = 0
    }

}

fun main() = Template().getTestVerifications().print()
//fun main() = Template().getResults().print()
//fun main() = Template().getVerifications().print()