package com.nami.aoc.task.solutions.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex
import kotlin.math.abs

class Y24D01 : Task<Y24D01.Input>(2024, 1) {

    override fun getRawInputTest() = InputSimplex(
        """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
        """.trimIndent()
    )

    data class Input(
        val left: List<Int>,
        val right: List<Int>
    )

    override fun getProcessedInput(raw: String): Input {
        val lines = raw.replace(" ", "").lines()

        val left = lines.map { it.substring(0, it.length / 2).toInt() }.sorted().toList()
        val right = lines.map { it.substring(it.length / 2, it.length).toInt() }.sorted().toList()

        return Input(left, right)
    }

    override fun getPartA() = object : Part<Input> {
        override fun solve(input: Input): Any {
            val distances = mutableListOf<Int>()

            for (i in input.left.indices)
                distances.add(abs(input.left[i] - input.right[i]))

            return distances.sum()
        }

        override fun bonus() = 3.0
    }

    override fun getPartB() = object : Part<Input> {
        override fun solve(input: Input): Any {
            var value = 0
            for (number in input.left) {
                val count = input.right.count { it == number }
                value += number * count
            }

            return value
        }

        override fun bonus() = 7.0
    }

}

fun main() = Y24D01().printResult()
