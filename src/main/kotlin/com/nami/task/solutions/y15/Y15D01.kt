package com.nami.task.solutions.y15

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputDuplex

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
            var count = 0
            for (c in input) {
                count++

                if (c == '(')
                    sum++

                if (c == ')')
                    sum--

                if (sum < 0)
                    break
            }

            return count
        }

        override fun bonus() = 10.0
    }
}

fun main() = Y15D01().printResult()
//fun main() = Y15D01().printVerification()
