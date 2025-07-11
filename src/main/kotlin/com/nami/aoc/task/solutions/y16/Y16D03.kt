package com.nami.aoc.task.solutions.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputDuplex

class Y16D03 : Task<List<Int>>(2016, 3) {

    override fun getRawInputTest() = InputDuplex(
        "5 10 25",
        """
            101 301 501
            102 302 502
            103 303 503
            201 401 601
            202 402 602
            203 403 603
        """.trimIndent()
    )

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

    override fun getPartA() = object : Part<List<Int>> {
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

    override fun getPartB() = object : Part<List<Int>> {
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

//fun main() = Y16D03().printResult()
fun main() = Y16D03().printVerification()
