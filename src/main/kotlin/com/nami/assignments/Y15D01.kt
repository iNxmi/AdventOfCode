package com.nami.assignments

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputDuplex
import com.nami.test.TestInputSimplex
import java.util.*

class Y15D01 : Assignment<String>(2015, 1) {

    override fun getRawTestInput(): TestInput {
        return TestInputDuplex("))(((((", "()())")
    }

    override fun getProcessedInput(raw: String): String {
        return raw
    }

    override fun solveA(input: String): Int {
        var sum = 0
        for (c in input.toCharArray()) {
            if (c == '(')
                sum++

            if (c == ')')
                sum--
        }

        return sum
    }

    override fun solveB(input: String): Int {
        var sum = 0
        var count = 0
        for (c in input.toCharArray()) {
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