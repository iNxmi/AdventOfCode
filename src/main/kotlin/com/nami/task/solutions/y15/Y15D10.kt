package com.nami.task.solutions.y15

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.Input
import com.nami.task.input.InputSimplex

class Y15D10 : Task<String>(2015, 10) {

    override fun getRawInputTest(): Input = InputSimplex("1")

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

    override fun getPartA() = object : Part<String> {
        override fun solve(input: String): Any = iterate(input, 40).length
        override fun test(input: String): Any = iterate(input, 5).length
        override fun bonus() = 5.0
    }

    override fun getPartB() = object : Part<String> {
        override fun solve(input: String): Any = iterate(input, 50).length
        override fun test(input: String) = null
        override fun bonus() = 10.0
    }

}

fun main() = Y15D10().printResult()
