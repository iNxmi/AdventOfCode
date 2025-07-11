package com.nami.aoc.task

interface Part<InputClass> {

    fun getResult(uid: UID, input: InputClass): Result {
        val timeStartNs = System.nanoTime()
        val value = solve(input)
        val timeNs = System.nanoTime() - timeStartNs
        val timeInSeconds = timeNs * 1E-9

        return Result(uid, this, value, timeInSeconds)
    }

    fun getResultTest(uid: UID, input: InputClass): Result {
        val timeStartNs = System.nanoTime()
        val value = test(input)
        val timeNs = System.nanoTime() - timeStartNs
        val timeInSeconds = timeNs * 1E-9

        return Result(uid, this, value, timeInSeconds)
    }

    fun getVerification(uid: UID, input: InputClass, expected: String?): Verification {
        val solution = getResult(uid, input)

        val status = if (expected == null) {
            Status.UNSOLVED
        } else if (solution.value.toString() != expected) {
            Status.FAILED
        } else {
            Status.SOLVED
        }

        return Verification(uid, this, status, expected, solution)
    }

    fun solve(input: InputClass): Any?
    fun test(input: InputClass): Any? = solve(input)
    fun bonus(): Double? = null
    fun comment(): String? = null

}