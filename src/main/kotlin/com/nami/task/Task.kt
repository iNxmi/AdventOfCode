package com.nami.task

import com.nami.task.solutions.y15.*
import com.nami.task.solutions.y23.Y23D01
import com.nami.task.solutions.y24.*
import com.nami.task.test.TestInput

abstract class Task<InputClass : Any>(
    val year: Int,
    val day: Int,
    val id: Int = getID(year, day)
) {

    companion object {
        fun getID(year: Int, day: Int): Int = year * 100 + day

        fun getAll(): Set<Task<*>> = setOf(
            Y15D01(),
            Y15D02(),
            Y15D03(),
            Y15D04(),
            Y15D05(),
            Y15D06(),
            Y15D07(),
            Y15D08(),
            Y15D09(),
            Y15D10(),
            Y15D11(),
            Y15D12(),
//            Y15D13(),
            Y15D14(),
            Y15D15(),
            Y15D16(),
//            Y15D17(),
            Y15D18(),
            Y23D01(),
            Y24D01(),
            Y24D02(),
            Y24D03(),
            Y24D04(),
            Y24D05(),
            Y24D06(),
            Y24D07(),
            Y24D08(),
            Y24D11(),
            Y24D15()
        )
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

        val aTimeStartNs = System.nanoTime()
        val a = solveA(input)
        val aTimeNs = System.nanoTime() - aTimeStartNs
        val aTimeS = aTimeNs * 1E-9

        val bTimeStartNs = System.nanoTime()
        val b = solveB(input)
        val bTimeNs = System.nanoTime() - bTimeStartNs
        val bTimeS = bTimeNs * 1E-9

        val result = Result(
            id, year, day,

            a, aTimeS,
            b, bTimeS,

            if (getRawTestInput() != null) solveATest(getProcessedInput(getRawTestInput()!!.getRawTestInputA())) else null,
            if (getRawTestInput() != null) solveBTest(getProcessedInput(getRawTestInput()!!.getRawTestInputB())) else null
        )

        return result
    }

}