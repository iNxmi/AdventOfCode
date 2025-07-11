package com.nami.aoc.task.solutions.y23

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y23D04 : Task<List<String>>(2023, 4) {

    override fun getRawInputTest() = null

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>> {
        override fun solve(input: List<String>) = null
        override fun bonus() = 2.0
    }

    override fun getPartB() = object : Part<List<String>> {
        override fun solve(input: List<String>) = null
    }

}

//fun main() = Y23D04().printResult()
fun main() = Y23D04().printVerification()
