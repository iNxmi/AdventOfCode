package com.nami.aoc.task

import io.github.oshai.kotlinlogging.KotlinLogging

abstract class Part<InputClass>(
    val task: Task<*>,
    val suffix: Suffix,

    val bonus: Double? = null,
    val comment: String? = null
) {

    protected val log = KotlinLogging.logger("${task.year}_${task.day}_$suffix")

    enum class Suffix(val string: String) { A("a"), B("b") }

    fun getResult(input: InputClass, test: Boolean = false): Result {
        val timeStartNs = System.nanoTime()
        val value = if (test) test(input) else solve(input)
        val timeEndNs = System.nanoTime()

        val time = (timeEndNs - timeStartNs) * 1e-9
        return Result(this,  value, time)
    }

    abstract fun solve(input: InputClass): Any?
    open fun test(input: InputClass): Any? = solve(input)

}