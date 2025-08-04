package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import org.joml.Vector3i

class Y2015D02 : Task<List<Vector3i>>(2015, 2) {

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val array = line.split("x")
            .map { it.toInt() }
            .toIntArray()

        Vector3i(array)
    }

    override fun getPartA() = object : Part<List<Vector3i>>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Vector3i>) = input.sumOf { dimension ->
            val length = dimension.x
            val width = dimension.y
            val height = dimension.z

            val x = length * width
            val y = width * height
            val z = height * length

            2 * (x + y + z) + minOf(x, y, z)
        }
    }

    override fun getPartB() = object : Part<List<Vector3i>>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<Vector3i>) = input.sumOf { dimension ->
            val length = dimension.x
            val width = dimension.y
            val height = dimension.z

            val sorted = listOf(length, width, height).sorted()
            val a = sorted[0]
            val b = sorted[1]

            2 * (a + b) + length * width * height
        }
    }

}

//fun main() = Y2015D02().getTestVerifications().print()
fun main() = Y2015D02().getVerifications().print()