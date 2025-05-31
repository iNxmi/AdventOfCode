package com.nami.task.solutions.y15

import com.nami.task.Task
import com.nami.task.test.TestInput
import com.nami.task.test.TestInputSimplex

class Y15D10 : Task<String>(2015, 10) {

    override fun getRawTestInput(): TestInput = TestInputSimplex("1")

    override fun getProcessedInput(raw: String): String = raw

    private fun process(value: String): String {
        val builder = StringBuilder()

        var counter = 1
        var charLast = value[0]
        for (i in 1..<value.length) {
            val charCurrent = value[i]
            if (charLast == charCurrent) {
                counter++
                continue
            } else {
                builder.append("$counter$charLast")
                counter = 1
                charLast = charCurrent
            }
        }
        builder.append("$counter$charLast")

        return builder.toString()
    }

    private fun iterate(value: String, iterations: Int): String {
        var result = value
        for (i in 0..<iterations)
            result = process(result)

        return result
    }

    override fun solveA(input: String): Any = iterate(input, 40).length

    override fun solveATest(input: String): Any = iterate(input, 5).length

    override fun solveB(input: String): Any = iterate(input, 50).length

    override fun solveBTest(input: String): Any? = null

}

fun main() = println(Y15D10().solve())
