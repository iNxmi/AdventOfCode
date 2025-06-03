package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.Task
import com.nami.task.test.TestInputSimplex
import org.joml.Vector3i

class Y15D02 : Task<List<Vector3i>>(2015, 2) {

    override fun getRawTestInput() = TestInputSimplex("1x1x10")

    override fun getProcessedInput(raw: String): List<Vector3i> {
        val result = mutableListOf<Vector3i>()
        raw.lines().forEach { line ->
            val dimensions = line.split("x")
                .map { it.toInt() }
                .toIntArray()
            result.add(Vector3i(dimensions))
        }

        return result
    }

    override fun solveA(input: List<Vector3i>): Any {
        var sum = 0

        for (dimension in input) {
            val length = dimension.x
            val width = dimension.y
            val height = dimension.z

            val x = length * width
            val y = width * height
            val z = height * length

            sum += 2 * (x + y + z)
            sum += setOf(x, y, z).min()
        }

        return sum
    }

    override fun solveB(input: List<Vector3i>): Any {
        var sum = 0

        for (dimension in input) {
            val length = dimension.x
            val width = dimension.y
            val height = dimension.z

            val sorted = mutableListOf(length, width, height).sorted()
            val a = sorted[0]
            val b = sorted[1]

            sum += 2 * (a + b)
            sum += length * width * height
        }

        return sum
    }

}

fun main() = Y15D02().solve().println()
