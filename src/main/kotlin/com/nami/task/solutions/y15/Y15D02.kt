package com.nami.task.solutions.y15

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputSimplex
import org.apache.commons.lang3.math.NumberUtils.min
import org.joml.Vector3i

class Y15D02 : Task<List<Vector3i>>(2015, 2) {

    override fun getRawInputTest() = InputSimplex("1x1x10")

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        line.split("x").map { it.toInt() }.toIntArray()
    }.map { Vector3i(it) }

    override fun getPartA() = object : Part<List<Vector3i>> {
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
                sum += min(x, y, z)
            }

            return sum
        }

        override fun bonus() = 5.0
    }

    override fun getPartB() = object : Part<List<Vector3i>> {
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

        override fun bonus() = 10.0
    }

}

fun main() = Y15D02().printResult()
