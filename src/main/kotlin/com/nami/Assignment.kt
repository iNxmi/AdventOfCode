package com.nami

import com.nami.test.TestInput

abstract class Assignment<T>(private val year: Int, private val day: Int) {

    private fun getRawInput(): String {
        return Input.get(year, day)
    }

    abstract fun getRawTestInput(): TestInput
    abstract fun getProcessedInput(raw: String): T
    abstract fun solveA(input: T): Number
    abstract fun solveB(input: T): Number

    fun id(): Int {
        return Utils.getID(year, day)
    }

    data class Solution(
        val id: Int,
        val year: Int,
        val day: Int,

        val a: Number,
        val aTest: Number,

        val b: Number,
        val bTest: Number
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
            solveA(testInputA),

            solveB(input),
            solveB(testInputB),
        )
    }

}