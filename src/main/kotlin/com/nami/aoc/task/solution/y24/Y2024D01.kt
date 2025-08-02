package com.nami.aoc.task.solution.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.abs

class Y2024D01 : Task<Y2024D01.Input>(2024, 1) {

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

    override fun getPartA() = object : Part<Input>(
        year, day, Type.A,
        bonus = 3.0
    ) {
        override fun solve(input: Input): Any {
            val distances = mutableListOf<Int>()

            for (i in input.left.indices)
                distances.add(abs(input.left[i] - input.right[i]))

            return distances.sum()
        }
    }

    override fun getPartB() = object : Part<Input>(
        year, day, Type.B,
        bonus = 7.0
    ) {
        override fun solve(input: Input): Any {
            var value = 0
            for (number in input.left) {
                val count = input.right.count { it == number }
                value += number * count
            }

            return value
        }
    }

}
