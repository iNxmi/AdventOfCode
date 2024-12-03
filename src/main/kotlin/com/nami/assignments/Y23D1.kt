package com.nami.assignments

import com.nami.Assignment
import java.util.*

class Y23D1 : Assignment<List<String>> {

    override fun year(): Int {
        return 2023
    }

    override fun day(): Int {
        return 1
    }

    override fun getProcessedInput(): List<String> {
        return getInput().lines()
    }

    private fun digitToWord(digit: Int): String {
        val array = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        return array[digit]
    }

    override fun solveA(): Int {
        val input = getProcessedInput()
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

    override fun solveB(): Int {
        val input = getProcessedInput()

        val digits = mutableMapOf<String, Int>()
        for (i in 0..9) {
            digits[i.toString()] = i
            digits[digitToWord(i)] = i
        }

        val numbers: MutableList<Int> = ArrayList()

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

fun main() {
    println(Y23D1().solveA())
    println(Y23D1().solveB())
}