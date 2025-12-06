package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.y25.Y2025D06.Problem

class Y2025D06 : Task<List<Problem>>(2025, 6) {

    enum class Operation {
        MULTIPLICATION, ADDITION
    }

    data class Problem(
        val numbers: List<Long>,
        val operation: Operation
    )

    override fun getProcessedInput(raw: String): List<Problem> {

        val normalized = raw.replace(Regex("[ \\t]+"), " ")
        val lines = normalized.lines().map { line -> line.trim() }

        val problems = mutableListOf<Problem>()
        for(i in 0 until lines[0].split(" ").size) {

            val numbers = mutableListOf<Long>()
            for(lineIndex in 0 until lines.size - 1)
                numbers.add(lines[lineIndex].split(" ")[i].toLong())

            val problem = Problem(
                numbers,
                if (lines[lines.size - 1].split(" ")[i] == "+") Operation.ADDITION else Operation.MULTIPLICATION
            )

            problems.add(problem)
        }

        return problems
    }

    override fun getPartA() = object : Part.A<List<Problem>>(this) {
        override fun solve(input: List<Problem>) = input.sumOf {
            if(it.operation == Operation.MULTIPLICATION) {
                var result = it.numbers[0]
                for(i in 1 until it.numbers.size)
                    result *= it.numbers[i]
                result
            } else {
                it.numbers.sum()
            }
        }
    }

    override fun getPartB() = object : Part.B<List<Problem>>(this) {
        override fun solve(input: List<Problem>) = null
    }

}

//fun main() = Y2025D06().getTestVerifications().print()
//fun main() = Y2025D06().getResults().print()
fun main() = Y2025D06().getVerifications().print()