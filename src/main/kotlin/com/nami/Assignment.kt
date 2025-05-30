package com.nami

import com.nami.test.TestInput

abstract class Assignment<T>(private val year: Int, private val day: Int) {

    private fun getRawInput(): String {
        return Input.get(year, day)
    }

    abstract fun getRawTestInput(): TestInput
    abstract fun getProcessedInput(raw: String): T
    abstract fun solveA(input: T): Any
    open fun solveATest(input: T): Any? = null
    abstract fun solveB(input: T): Any
    open fun solveBTest(input: T): Any? = null

    fun id(): Int {
        return Utils.getID(year, day)
    }

    data class Solution(
        val id: Int,
        val year: Int,
        val day: Int,

        val a: Any,
        val b: Any,

        val aTest: Any?,
        val bTest: Any?
    )

    fun solve(): Solution {
        val input = getProcessedInput(getRawInput())
        val testInputA = getProcessedInput(getRawTestInput().getRawTestInputA())
        val testInputB = getProcessedInput(getRawTestInput().getRawTestInputB())

        return Solution(
            id(),
            year,
            day,

            solveA(input),
            solveB(input),

            solveATest(testInputA),
            solveBTest(testInputB),
        )
    }

}