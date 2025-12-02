package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2025D02 : Task<Set<LongRange>>(2025, 2) {

    override fun getProcessedInput(raw: String) = raw.split(",").map { range ->
        val split = range.split("-")
        LongRange(split[0].toLong(), split[1].toLong())
    }.toSet()

    override fun getPartA() = object : Part.A<Set<LongRange>>(this) {
        private val REGEX = Regex("(.+)\\1")
        override fun solve(input: Set<LongRange>) =
            input.flatMap { it.toSet() }.filter { it.toString().matches(REGEX) }.sum()
    }

    override fun getPartB() = object : Part.B<Set<LongRange>>(this) {
        private val REGEX = Regex("(.+)\\1+")
        override fun solve(input: Set<LongRange>)=
            input.flatMap { it.toSet() }.filter { it.toString().matches(REGEX) }.sum()
    }

}

//fun main() = Y2025D02().getTestVerifications().print()
//fun main() = Y2025D02().getResults().print()
fun main() = Y2025D02().getVerifications().print()