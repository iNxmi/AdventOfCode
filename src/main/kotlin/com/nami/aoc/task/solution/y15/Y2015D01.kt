package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.exception.AOCException
import com.nami.aoc.task.solution.exception.AOCExceptionNoSolutionFound

class Y2015D01 : Task<String>(2015, 1) {

    override fun getProcessedInput(raw: String) = raw

    override fun getPartA() = object : Part<String>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: String): Any {
            val opening = input.count { it == '(' }
            val closing = input.count { it == ')' }
            return opening - closing
        }
    }

    override fun getPartB() = object : Part<String>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: String): Any {
            var floor = 0
            input.withIndex().forEach { (index, char) ->
                floor += when (char) {
                    '(' -> 1
                    ')' -> -1
                    else -> throw AOCException(log, "Invalid character '$char'")
                }

                if (floor < 0)
                    return index + 1
            }

            throw AOCExceptionNoSolutionFound(log)
        }
    }

}

//fun main() = Y2015D01().getTestVerifications().print()
fun main() = Y2015D01().getResults().print()
//fun main() = Y2015D01().getVerifications().print()
