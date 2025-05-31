package com.nami

import com.nami.test.TestInput

abstract class Assignment<InputClass : Any, SolutionClass : Any>(val year: Int, val day: Int) {

    private fun getRawInput(): String = Remote.getInput(year, day)

    abstract fun getRawTestInput(): TestInput?
    abstract fun getProcessedInput(raw: String): InputClass
    abstract fun solveA(input: InputClass): SolutionClass
    open fun solveATest(input: InputClass): SolutionClass? = null
    abstract fun solveB(input: InputClass): SolutionClass
    open fun solveBTest(input: InputClass): SolutionClass? = null

    fun id(): Int = Utils.getID(year, day)

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

        return Solution(
            id(),
            year,
            day,

            solveA(input),
            solveB(input),

            if (getRawTestInput() != null) solveATest(getProcessedInput(getRawTestInput()!!.getRawTestInputA())) else null,
            if (getRawTestInput() != null) solveBTest(getProcessedInput(getRawTestInput()!!.getRawTestInputB())) else null
        )
    }

}