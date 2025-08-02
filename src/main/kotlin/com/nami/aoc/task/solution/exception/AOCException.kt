package com.nami.aoc.task.solution.exception

import com.nami.aoc.task.Part

open class AOCException(
    val year: Int,
    val day: Int,
    val part: Part.Type,
    message: String,
) : RuntimeException("${year}_${day}_$part -> $message")