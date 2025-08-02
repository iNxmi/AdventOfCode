package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2016D10 : Task<List<String>>(2016, 10) {

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>>(
        year, day, Type.A
    ) {
        override fun solve(input: List<String>) = null
    }

    override fun getPartB() = object : Part<List<String>>(
        year, day, Type.B
    ) {
        override fun solve(input: List<String>) = null
    }

}