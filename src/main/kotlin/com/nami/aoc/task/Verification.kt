package com.nami.aoc.task

data class Verification(
    val result: Result,
    val expected: String?
) {

    enum class Status {
        UNSOLVED,
        FAILED,
        SOLVED
    }

    val status = when (expected) {
        null -> Status.UNSOLVED
        result.value.toString() -> Status.SOLVED
        else -> Status.FAILED
    }

}