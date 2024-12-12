package com.nami

import com.nami.test.TestInput

abstract class Assignment<T>(private val year: Int, private val day: Int) {

    companion object {
        fun getSolutions(assignments: List<Assignment<*>>): Map<Int, Solution> {
            val map = mutableMapOf<Int, Solution>()
            for (assignment in assignments)
                map[assignment.id()] = assignment.solve()

            return map
        }
    }

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

    data class Solution(val a: Number, val aTest: Number, val b: Number, val bTest: Number)

    fun solve(): Solution {
        val input = getProcessedInput(getRawInput())
        val testInputA = getProcessedInput(getRawTestInput().getRawTestInputA())
        val testInputB = getProcessedInput(getRawTestInput().getRawTestInputB())

        return Solution(
            solveA(input),
            solveA(testInputA),
            solveB(input),
            solveB(testInputB),
        )
    }

}