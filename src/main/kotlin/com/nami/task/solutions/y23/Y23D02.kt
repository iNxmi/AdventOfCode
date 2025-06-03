package com.nami.task.solutions.y23

import com.nami.println
import com.nami.task.Task

class Y23D02 : Task<List<String>>(2023, 2) {

    override fun getRawTestInput() = null

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun solveA(input: List<String>) = null
    override fun solveB(input: List<String>) = null

    override fun bonusA() = 2.0
    override fun bonusB() = 3.0

}

fun main() = Y23D02().solve().println()
