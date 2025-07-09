package com.nami.task

interface Part<InputClass> {

    data class Result(val value: Any?, val timeInSeconds: Double)

    fun getResult(input: InputClass): Result {
        val timeStartNs = System.nanoTime()
        val value = solve(input)
        val timeNs = System.nanoTime() - timeStartNs
        val timeInSeconds = timeNs * 1E-9

        return Result(value, timeInSeconds)
    }

    fun getResultTest(input: InputClass): Result {
        val timeStartNs = System.nanoTime()
        val value = test(input)
        val timeNs = System.nanoTime() - timeStartNs
        val timeInSeconds = timeNs * 1E-9

        return Result(value, timeInSeconds)
    }

    data class Verification(val status: Status, val expected: String?, val result: Result) {
        enum class Status { FAILED, UNSOLVED, SOLVED }
    }

    fun getVerification(input: InputClass, expected: String?): Verification {
        val solution = getResult(input)

        val status = if (expected == null) {
            Verification.Status.UNSOLVED
        } else if (solution.value.toString() != expected) {
            Verification.Status.FAILED
        } else {
            Verification.Status.SOLVED
        }

        return Verification(status, expected, solution)
    }

    fun solve(input: InputClass): Any?
    fun test(input: InputClass): Any? = solve(input)
    fun bonus(): Double? = null
    fun comment(): String? = null

}