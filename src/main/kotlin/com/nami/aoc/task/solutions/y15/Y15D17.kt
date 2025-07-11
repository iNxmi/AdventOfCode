package com.nami.aoc.task.solutions.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex

class Y15D17 : Task<List<Int>>(2015, 17) {

    override fun getRawInputTest() = InputSimplex(
        """
        20
        15
        10
        5
        5
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String): List<Int> = raw.lines().map { it.toInt() }

    override fun getPartA() = object : Part<List<Int>> {
        override fun solve(input: List<Int>) = null
    }

    override fun getPartB() = object : Part<List<Int>> {
        override fun solve(input: List<Int>) = null
    }

}

fun main() = Y15D17().printResults()
//fun main() = Y15D17().printVerification()