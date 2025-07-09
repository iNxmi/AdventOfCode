package com.nami.task.solutions.y23

import com.nami.println
import com.nami.task.Part
import com.nami.task.Task

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

fun main() = Y23D03().printResult()
