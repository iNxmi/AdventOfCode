package com.nami.aoc.task.solution.y15

import com.nami.aoc.combinations
import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D17 : Task<List<Int>>(2015, 17) {

    override fun getProcessedInput(raw: String) = raw.lines().map { it.toInt() }

    private fun getCombinations(values: List<Int>, target: Int) = values.combinations().filter { it.sum() == target }

    override fun getPartA() = object : Part<List<Int>>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Int>) = getCombinations(input, 150).size
        override fun test(input: List<Int>) = getCombinations(input, 25).size
    }

    override fun getPartB() = object : Part<List<Int>>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        fun getMinimalCombinations(values: List<Int>, target: Int): List<List<Int>> {
            val combinations = getCombinations(values, target)
            val sorted = combinations.sortedBy { it.size }
            val min = sorted[0].size

            return combinations.filter { it.size == min }
        }

        override fun solve(input: List<Int>) = getMinimalCombinations(input, 150).size
        override fun test(input: List<Int>) = getMinimalCombinations(input, 25).size
    }

}

//fun main() = Y2015D17().getTestVerifications().print()
//fun main() = Y2015D17().getResults().print()
fun main() = Y2015D17().getVerifications().print()