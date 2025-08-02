package com.nami.aoc.task

abstract class Part<InputClass>(
    val task: Task<*>,
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
        return Result(this, test, value, time)
    }

    abstract fun solve(input: InputClass): Any?
    open fun test(input: InputClass): Any? = solve(input)

}