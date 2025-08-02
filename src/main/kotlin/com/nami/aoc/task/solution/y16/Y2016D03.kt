package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2016D03 : Task<List<Int>>(2016, 3) {

    override fun getProcessedInput(raw: String) = raw.lines().flatMap { line ->
        val regex = ("\\d+").toRegex()
        regex.findAll(line).map { it.groupValues[0].toInt() }.toList()
    }

    fun condition(x: Int, y: Int, z: Int): Boolean {
        val a = x + y > z
        val b = y + z > x
        val c = x + z > y

        return a && b && c
    }

    override fun getPartA() = object : Part<List<Int>>(
        year, day, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Int>): Any {
            var count = 0
            for (i in 0..<(input.size / 3)) {
                val x = input[i * 3]
                val y = input[i * 3 + 1]
                val z = input[i * 3 + 2]

                if (condition(x, y, z))
                    count++
            }

            return count
        }
    }

    override fun getPartB() = object : Part<List<Int>>(
        year, day, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<Int>): Any {
            var count = 0
            for (i in 0..<input.size step 9)
                for (j in 0..2) {
                    val x = input[i + j]
                    val y = input[i + j + 3]
                    val z = input[i + j + 6]

                    if (condition(x, y, z))
                        count++
                }

            return count
        }
    }

}
