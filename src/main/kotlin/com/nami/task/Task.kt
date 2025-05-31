package com.nami.task

import com.nami.task.test.TestInput

abstract class Task<InputClass : Any>(
    val year: Int,
    val day: Int,
    val id: Int = getID(year, day)
) {

    companion object {
        fun getID(year: Int, day: Int): Int = year * 100 + day
    }

    private fun getRawInput(): String = Remote.getInput(year, day)
    abstract fun getRawTestInput(): TestInput?

    abstract fun getProcessedInput(raw: String): InputClass

    open fun solveA(input: InputClass): Any? = null
    open fun solveATest(input: InputClass): Any? = solveA(input)

    open fun solveB(input: InputClass): Any? = null
    open fun solveBTest(input: InputClass): Any? = solveB(input)

    fun solve(): Result {
        val input = getProcessedInput(getRawInput())

        val result = Result(
            id,
            year,
            day,

            solveA(input),
            solveB(input),

            if (getRawTestInput() != null) solveATest(getProcessedInput(getRawTestInput()!!.getRawTestInputA())) else null,
            if (getRawTestInput() != null) solveBTest(getProcessedInput(getRawTestInput()!!.getRawTestInputB())) else null
        )

        return result
    }

}