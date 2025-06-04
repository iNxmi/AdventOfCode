package com.nami.task

interface SubTask<InputClass> {

    fun getResult(input: InputClass): Result {
        val timeStartNs = System.nanoTime()
        val result = solve(input)
        val timeNs = System.nanoTime() - timeStartNs
        val timeS = timeNs * 1E-9

        return Result(result, timeS)
    }

    fun getResultTest(input: InputClass): Result {
        val timeStartNs = System.nanoTime()
        val result = test(input)
        val timeNs = System.nanoTime() - timeStartNs
        val timeS = timeNs * 1E-9

        return Result(result, timeS)
    }

    fun solve(input: InputClass): Any?
    fun test(input: InputClass): Any? = solve(input)
    fun bonus(): Double? = null
    fun comment(): String? = null

    data class Result(
        val result: Any?,
        val timeS: Double
    )

}