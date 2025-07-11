package com.nami.aoc.task

data class Result(
    val uid: UID,
    val part: Part<*>,
    val value: Any?,
    val timeInSeconds: Double
)