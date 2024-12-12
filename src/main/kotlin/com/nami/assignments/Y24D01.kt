package com.nami.assignments

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import kotlin.math.abs
import kotlin.streams.toList

class Y24D01 : Assignment<Pair<List<Int>, List<Int>>>(2024, 1) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()
        )
    }

    override fun getProcessedInput(raw: String): Pair<List<Int>, List<Int>> {
        val lines = raw.replace(" ", "").lines()

        val listLeft = lines.stream().mapToInt { it.substring(0, 5).toInt() }.sorted().toList()
        val listRight = lines.stream().mapToInt { it.substring(5, 10).toInt() }.sorted().toList()

        return Pair(listLeft, listRight)
    }

    override fun solveA(input: Pair<List<Int>, List<Int>>): Int {
        val distances = mutableListOf<Int>()

        for (i in input.first.indices)
            distances.add(abs(input.first[i] - input.second[i]))

        return distances.sum()
    }

    override fun solveB(input: Pair<List<Int>, List<Int>>): Int {
        var value = 0
        for (number in input.first) {
            val count = input.second.count { it == number }
            value += number * count
        }

        return value
    }

}