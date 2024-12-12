package com.nami.assignments

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import kotlin.math.min

class Y15D02 : Assignment<List<Triple<Int, Int, Int>>>(2015, 2) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex("1x1x10")
    }

    override fun getProcessedInput(raw: String): List<Triple<Int, Int, Int>> {
        val lines = raw.lines()

        val result = mutableListOf<Triple<Int, Int, Int>>()
        lines.forEach { line ->
            val dimensions = line.split("x").map { it.toInt() }
            result.add(Triple(dimensions[0], dimensions[1], dimensions[2]))
        }

        return result
    }

    override fun solveA(input: List<Triple<Int, Int, Int>>): Int {
        var sum = 0

        for (dimension in input) {
            val l = dimension.first
            val w = dimension.second
            val h = dimension.third

            val x = l * w
            val y = w * h
            val z = h * l

            sum += 2 * (x + y + z)
            sum += min(x, min(y, z))
        }

        return sum
    }

    override fun solveB(input: List<Triple<Int, Int, Int>>): Int {
        var sum = 0

        for (dimension in input) {
            val l = dimension.first
            val w = dimension.second
            val h = dimension.third

            val sortedList = mutableListOf(l, w, h).sorted()
            val a = sortedList[0]
            val b = sortedList[1]

            sum += 2 * (a + b)
            sum += l * w * h
        }

        return sum
    }

}