package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.input.InputSimplex

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

    override fun getSubTaskA() = object : SubTask<List<Int>> {
        override fun solve(input: List<Int>): Any {
            println(input)
            return ""
        }
    }

    override fun getSubTaskB() = object : SubTask<List<Int>> {
        override fun solve(input: List<Int>) = null
    }

}

fun main() = Y15D17().getResult().println()