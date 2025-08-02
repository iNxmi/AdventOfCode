package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D17 : Task<List<Int>>(2015, 17) {

    override fun getProcessedInput(raw: String): List<Int> = raw.lines().map { it.toInt() }

    override fun getPartA() = object : Part<List<Int>>(
        year, day, Type.A
    ) {
        override fun solve(input: List<Int>) = null
    }

    override fun getPartB() = object : Part<List<Int>>(
        year, day, Type.B
    ) {
        override fun solve(input: List<Int>) = null
    }

}