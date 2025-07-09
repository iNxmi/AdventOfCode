package com.nami.task.solutions.y23

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputDuplex
import java.util.*

class Y23D01 : Task<List<String>>(2023, 1) {

    override fun getRawInputTest() = InputDuplex(
        """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
        """.trimIndent(), """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String) = raw.lines()

    private fun digitToWord(digit: Int): String {
        val array = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        return array[digit]
    }

    override fun getPartA() = object : Part<List<String>> {
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

        override fun bonus() = 2.0
    }

    override fun getPartB() = object : Part<List<String>> {
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

        override fun bonus() = 3.0
    }

}

fun main() = Y23D01().printResult()
