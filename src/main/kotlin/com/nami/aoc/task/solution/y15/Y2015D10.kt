package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D10 : Task<String>(2015, 10) {

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
        repeat(iterations) {
            result = process(result)
        }

        return result
    }

    override fun getPartA() = object : Part<String>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: String): Any = iterate(input, 40).length
        override fun test(input: String): Any = iterate(input, 1).length
    }

    override fun getPartB() = object : Part<String>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: String): Any = iterate(input, 50).length
        override fun test(input: String) = null
    }

}
