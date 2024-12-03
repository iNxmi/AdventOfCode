package com.nami

abstract class Assignment<T>(private val year: Int, private val day: Int) {

    companion object {
        fun getSolutions(assignments: List<Assignment<*>>): Map<Int, Pair<Number, Number>> {
            val map = mutableMapOf<Int, Pair<Number, Number>>()
            for (assignment in assignments)
                map[assignment.id()] = Pair(assignment.solveA(), assignment.solveB())

            return map
        }
    }

    private fun getRawInput(): String {
        return Input.get(year, day)
    }

    abstract fun getProcessedInput(raw: String): T

    abstract fun solveA(input: T): Number
    fun solveA(): Number {
        return solveA(getProcessedInput(getRawInput()))
    }

    abstract fun solveB(input: T): Number
    fun solveB(): Number {
        return solveB(getProcessedInput(getRawInput()))
    }

    fun id(): Int {
        return Utils.getID(year, day)
    }

}