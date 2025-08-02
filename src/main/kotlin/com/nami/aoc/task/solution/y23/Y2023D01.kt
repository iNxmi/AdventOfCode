package com.nami.aoc.task.solution.y23

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import java.util.*

class Y2023D01 : Task<List<String>>(2023, 1) {

    override fun getProcessedInput(raw: String) = raw.lines()

    private fun digitToWord(digit: Int): String {
        val array = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        return array[digit]
    }

    override fun getPartA() = object : Part<List<String>>(
        year, day, Type.A,
        bonus = 2.0
    ) {
        override fun solve(input: List<String>): Any {
            val numbers = mutableListOf<Int>()

            for (line in input) {
                val chars = line.toCharArray()

                val digits = mutableListOf<Int>()
                for (c in chars)
                    if (c.isDigit())
                        digits.add(c.digitToIntOrNull()!!)

                numbers.add(digits.first() * 10 + digits.last())
            }

            return numbers.sum()
        }
    }

    override fun getPartB() = object : Part<List<String>>(
        year, day, Type.B,
        bonus = 3.0
    ) {
        override fun solve(input: List<String>): Any {
            val digits = mutableMapOf<String, Int>()
            for (i in 0..9) {
                digits[i.toString()] = i
                digits[digitToWord(i)] = i
            }

            val numbers: MutableList<Int> = mutableListOf()

            for (line in input) {
                val map = TreeMap<Int, Int>()

                for (d in digits.keys) {
                    var index = line.indexOf(d)
                    if (index != -1)
                        map[index] = digits[d]!!

                    index = line.lastIndexOf(d)
                    if (index != -1)
                        map[index] = digits[d]!!
                }

                val result = map.firstEntry().value * 10 + map.lastEntry().value
                numbers.add(result)
            }

            return numbers.sum()
        }
    }

}
