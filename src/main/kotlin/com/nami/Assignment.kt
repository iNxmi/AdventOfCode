package com.nami

import com.nami.test.TestInput

abstract class Assignment<InputClass : Any, SolutionClass : Any>(val year: Int, val day: Int) {

    private fun getRawInput(): String {
        return Input.get(year, day)
    }

    abstract fun getRawTestInput(): TestInput
    abstract fun getProcessedInput(raw: String): InputClass
    abstract fun solveA(input: InputClass): SolutionClass
    open fun solveATest(input: InputClass): SolutionClass? = null
    abstract fun solveB(input: InputClass): SolutionClass
    open fun solveBTest(input: InputClass): SolutionClass? = null

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