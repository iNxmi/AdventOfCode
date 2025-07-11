package com.nami.aoc.task

data class Verification(
    val uid: UID,
    val part: Part<*>,
    val status: Status,
    val expected: String?,
    val result: Result
)