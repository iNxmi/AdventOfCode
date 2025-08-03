package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.exception.IllegalCharacterException
import com.nami.aoc.task.solution.exception.NoSolutionFoundException

class Y2015D01 : Task<String>(2015, 1) {

    override fun getProcessedInput(raw: String) = raw

    override fun getPartA() = object : Part<String>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: String): Any {
            val opening = input.count { it == '(' }
            val closing = input.count { it == ')' }
            log.error { opening }
            return opening - closing
        }
    }

    override fun getPartB() = object : Part<String>(
        this, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: String): Any {
            var sum = 0
            input.withIndex().forEach { (index, char) ->
                sum += when (char) {
                    '(' -> 1
                    ')' -> -1
                    else -> throw IllegalCharacterException(year, day, type, setOf('(', ')'), char)
                }

                if (sum < 0)
                    return index + 1
            }

            throw NoSolutionFoundException(year, day, type)
        }
    }

}

//fun main() = Y2015D01().getTestVerifications().print()
fun main() = Y2015D01().getResults().print()
//fun main() = Y2015D01().getVerifications().print()
