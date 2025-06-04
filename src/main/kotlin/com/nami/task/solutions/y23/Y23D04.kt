package com.nami.task.solutions.y23

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task

class Y23D04 : Task<List<String>>(2023, 4) {

    override fun getRawInputTest() = null

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getSubTaskA() = object : SubTask<List<String>> {
        override fun solve(input: List<String>) = null
        override fun bonus() = 2.0
    }

    override fun getSubTaskB() = object : SubTask<List<String>> {
        override fun solve(input: List<String>) = null
    }

}

fun main() = Y23D03().getResult().println()
