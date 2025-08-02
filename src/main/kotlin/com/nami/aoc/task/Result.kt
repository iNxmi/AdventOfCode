package com.nami.aoc.task

data class Result(
    val year: Int,
    val day: Int,
    val part: Part.Type,
    val test: Boolean,
    val value: Any?,
    val timeInSeconds: Double
) {

    fun getVerification(): Verification? {
        if (test)
            return null

        val solutions = Remote.getSolutions(year, day)
        val expected = solutions[part]
        return Verification(this, expected)
    }

}