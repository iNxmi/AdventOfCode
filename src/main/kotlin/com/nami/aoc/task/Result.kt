package com.nami.aoc.task

data class Result(
    val part: Part<*>,
    val value: Any?,
    val timeInSeconds: Double
) {

    fun getVerification(): Verification? {
        val solutions = Remote.getSolutions(part.task.year, part.task.day)
        val expected = solutions[part.type]
        return Verification(this, expected)
    }

}