package com.nami.aoc.task.solution.y15

import com.nami.aoc.divisors
import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.exception.AOCExceptionNoSolutionFound

class Y2015D20 : Task<Int>(2015, 20) {

    override fun getProcessedInput(raw: String) = raw.toInt()

    override fun getPartA() = object : Part<Int>(
        this, Suffix.A
    ) {
        override fun solve(input: Int): Any {
            for (house in 1..input) {
                val presents = house.divisors().sum() * 10
                if (presents >= input)
                    return house
            }

            throw AOCExceptionNoSolutionFound(log)
        }
    }

    override fun getPartB() = object : Part<Int>(
        this, Suffix.B
    ) {
        override fun solve(input: Int): Any {
            for (house in 1..input) {
                val presents = house.divisors()
                    .filterNot { divisor -> divisor * 50 < house }
                    .sum() * 11
                if (presents >= input)
                    return house
            }

            throw AOCExceptionNoSolutionFound(log)
        }
    }

}

//fun main() = Y2015D20().getTestVerifications().print()
//fun main() = Y2015D20().getResults().print()
fun main() = Y2015D20().getVerifications().print()