package com.nami.aoc.task.solution.exception

import com.nami.aoc.task.Part

class IllegalCharacterException(
    year: Int, day: Int, part: Part.Type,
    val valid: Set<Char>,
    val actual: Char
) : AOCException(year, day, part, "valid=$valid, actual=$actual")