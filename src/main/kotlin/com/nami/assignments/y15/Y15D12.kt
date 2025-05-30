package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y15D12 : Assignment<String, Int>(2015, 12) {

    override fun getRawTestInput(): TestInput = TestInputSimplex("[1,2,3]")

    override fun getProcessedInput(raw: String): String = raw

    override fun solveA(input: String): Int {
        return ("(-*(\\d+))").toRegex().findAll(input).sumOf { it.value.toInt() }
    }

    override fun solveB(input: String): Int {
        return -1
    }

}

fun main() = println(Y15D12().solve())
