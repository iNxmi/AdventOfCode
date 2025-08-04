package com.nami.aoc.task.solution

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Template : Task<List<String>>(0, 0) {

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

fun main() = Template().getTestVerifications().print()
//fun main() = Template().getResults().print()
//fun main() = Template().getVerifications().print()