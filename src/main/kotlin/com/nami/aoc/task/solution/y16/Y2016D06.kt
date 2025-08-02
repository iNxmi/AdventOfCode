package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2016D06 : Task<List<String>>(2016, 6) {

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<String>): Any {
            val length = input[0].length
            val count = Array(length) { mutableMapOf<Char, Int>() }
            input.forEach { line ->
                for (i in 0 until length) {
                    val char = line[i]
                    count[i][char] = count[i].getOrDefault(char, 0) + 1
                }
            }

            val sb = StringBuilder()
            count.forEach { map ->
                val sorted = map.toList().sortedByDescending { (_, value) -> value }
                sb.append(sorted.first().first)
            }

            return sb.toString()
        }
    }

    override fun getPartB() = object : Part<List<String>>(
        this,Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<String>): Any {
            val length = input[0].length
            val count = Array(length) { mutableMapOf<Char, Int>() }
            input.forEach { line ->
                for (i in 0 until length) {
                    val char = line[i]
                    count[i][char] = count[i].getOrDefault(char, 0) + 1
                }
            }

            val sb = StringBuilder()
            count.forEach { map ->
                val sorted = map.toList().sortedBy { (_, value) -> value }
                sb.append(sorted.first().first)
            }

            return sb.toString()
        }
    }
}
