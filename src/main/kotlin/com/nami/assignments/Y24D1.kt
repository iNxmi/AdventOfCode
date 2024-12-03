package com.nami.assignments

import com.nami.Assignment
import kotlin.math.abs
import kotlin.streams.toList

class Y24D1 : Assignment<Pair<List<Int>, List<Int>>> {

    override fun year(): Int {
        return 2024
    }

    override fun day(): Int {
        return 1
    }

    override fun getProcessedInput(): Pair<List<Int>, List<Int>> {
        val lines = getInput().replace(" ", "").lines()

        val listLeft = lines.stream().mapToInt { it.substring(0, 5).toInt() }.sorted().toList()
        val listRight = lines.stream().mapToInt { it.substring(5, 10).toInt() }.sorted().toList()

        return Pair(listLeft, listRight)
    }

    override fun solveA(): Int {
        val input = getProcessedInput()

        val distances = mutableListOf<Int>()

        for (i in input.first.indices)
            distances.add(abs(input.first[i] - input.second[i]))

        return distances.sum()
    }

    override fun solveB(): Int {
        val input = getProcessedInput()

        var value = 0
        for (number in input.first) {
            val count = input.second.count { it == number }
            value += number * count
        }

        return value
    }

}

fun main() {
    println(Y24D1().solveA())
    println(Y24D1().solveB())
}