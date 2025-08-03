package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import org.joml.Vector3i

class Y2015D02 : Task<List<Vector3i>>(2015, 2) {

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        line.split("x").map { it.toInt() }.toIntArray()
    }.map { Vector3i(it) }

    override fun getPartA() = object : Part<List<Vector3i>>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Vector3i>): Any {
            var sum = 0

            for (dimension in input) {
                val length = dimension.x
                val width = dimension.y
                val height = dimension.z

                val x = length * width
                val y = width * height
                val z = height * length

                sum += 2 * (x + y + z)
                sum += minOf(x, y, z)
            }

            return sum
        }
    }

    override fun getPartB() = object : Part<List<Vector3i>>(
        this, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<Vector3i>): Any {
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

}
