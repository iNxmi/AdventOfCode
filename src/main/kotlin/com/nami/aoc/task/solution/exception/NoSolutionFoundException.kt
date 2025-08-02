package com.nami.aoc.task.solution.exception

import com.nami.aoc.task.Part

class NoSolutionFoundException(
    year: Int, day: Int, part: Part.Type
) : AOCException(year, day, part, "No Solution found")