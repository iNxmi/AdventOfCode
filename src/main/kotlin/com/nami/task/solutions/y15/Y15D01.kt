package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.Task
import com.nami.task.test.TestInputDuplex

class Y15D01 : Task<String>(2015, 1) {

    override fun getRawTestInput() = TestInputDuplex("))(((((", "()())")

    override fun getProcessedInput(raw: String) = raw

    override fun solveA(input: String): Any {
        val opening = input.count { it == '(' }
        val closing = input.count { it == ')' }
        return opening - closing
    }

    override fun solveB(input: String): Any {
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

}

fun main() = Y15D01().solve().println()
