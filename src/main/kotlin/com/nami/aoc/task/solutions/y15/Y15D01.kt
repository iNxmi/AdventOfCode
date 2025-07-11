package com.nami.aoc.task.solutions.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputDuplex

class Y15D01 : Task<String>(2015, 1) {

    override fun getRawInputTest() = InputDuplex("))(((((", "()())")

    override fun getProcessedInput(raw: String) = raw

    override fun getPartA() = object : Part<String> {
        override fun solve(input: String): Any {
            val opening = input.count { it == '(' }
            val closing = input.count { it == ')' }
            return opening - closing
        }

        override fun bonus() = 5.0
    }

    override fun getPartB() = object : Part<String> {
        override fun solve(input: String): Any {
            var sum = 0
            input.withIndex().forEach { (index, char) ->
                if (char == '(')
                    sum++

                if (char == ')')
                    sum--

                if (sum < 0)
                    return index + 1
            }

            throw IllegalStateException("No solution found")
        }

        override fun bonus() = 10.0

    }
}

//fun main() = Y15D01().printResult()
fun main() = Y15D01().printVerification()
