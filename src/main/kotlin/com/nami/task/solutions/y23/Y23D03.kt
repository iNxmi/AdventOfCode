package com.nami.task.solutions.y23

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task

class Y23D03 : Task<List<String>>(2023, 3) {

    override fun getRawInputTest() = null

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getA() = object: SubTask<List<String>> {
        override fun solve(input: List<String>) = null
        override fun bonus() = 2.0
    }

    override fun getB() = object: SubTask<List<String>> {
        override fun solve(input: List<String>) = null
        override fun bonus() = 3.0
    }

}

fun main() = Y23D03().getResult().println()
