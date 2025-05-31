package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import org.joml.Vector3i
import kotlin.math.min

class Y15D02 : Assignment<List<Vector3i>>(2015, 2) {

    override fun getRawTestInput() = TestInputSimplex("1x1x10")

    override fun getProcessedInput(raw: String): List<Vector3i> {
        val lines = raw.lines()

        val result = mutableListOf<Vector3i>()
        lines.forEach { line ->
            val dimensions = line.split("x").map { it.toInt() }
            result.add(Vector3i(dimensions[0], dimensions[1], dimensions[2]))
        }

        return result
    }

    override fun solveA(input: List<Vector3i>): Any {
        var sum = 0

        for (dimension in input) {
            val l = dimension.x
            val w = dimension.y
            val h = dimension.z

            val x = l * w
            val y = w * h
            val z = h * l

            sum += 2 * (x + y + z)
            sum += min(x, min(y, z))
        }

        return sum
    }

    override fun solveB(input: List<Vector3i>): Any {
        var sum = 0

        for (dimension in input) {
            val l = dimension.x
            val w = dimension.y
            val h = dimension.z

            val sortedList = mutableListOf(l, w, h).sorted()
            val a = sortedList[0]
            val b = sortedList[1]

            sum += 2 * (a + b)
            sum += l * w * h
        }

        return sum
    }

}

fun main() = println(Y15D02().solve())
