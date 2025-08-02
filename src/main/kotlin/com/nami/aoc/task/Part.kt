package com.nami.aoc.task

abstract class Part<InputClass>(
    val year: Int,
    val day: Int,
    val type: Type,

    val bonus: Double? = null,
    val comment: String? = null
) {

    enum class Type(val string: String) { A("a"), B("b") }

    fun getResult(input: InputClass, test: Boolean = false): Result {
        val timeStartNs = System.nanoTime()
        val value = if (test) test(input) else solve(input)
        val timeEndNs = System.nanoTime()

        val time = (timeEndNs - timeStartNs) * 1e-9
        return Result(year, day, type, test, value, time)
    }

    abstract fun solve(input: InputClass): Any?
    open fun test(input: InputClass): Any? = solve(input)

}