package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D11 : Task<String>(2015, 11) {

    private val segments = mutableSetOf<String>()
    private val alphabet = "abcdefghijklmnopqrstuvwxyz"

    init {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val length = 3
        for (i in 0..<alphabet.length - length + 1)
            segments.add(alphabet.substring(i, i + length))
    }

    override fun getProcessedInput(raw: String): String = raw

    private fun numberToAlphabetString(number: Long): String {
        val base = alphabet.length

        var n = number
        val result = StringBuilder()

        do {
            val remainder = (n % base).toInt()
            result.append(alphabet[remainder])
            n = n / base - 1
        } while (n >= 0)

        return result.reverse().toString()
    }

    private fun alphabetStringToNumber(s: String): Long {
        val base = alphabet.length
        var result = 0L

        for (char in s) {
            val digit = alphabet.indexOf(char)
            require(digit >= 0) { "Invalid character '$char' in input" }
            result = result * base + (digit + 1)
        }

        return result - 1
    }

    private fun containsStraightABC(value: String): Boolean = segments.any { value.contains(it) }
    private fun containsIOL(value: String): Boolean = value.contains(("i|o|l").toRegex())
    private fun contains2NonOverlapPairs(value: String): Boolean {
        var count = 0
        var i = 0
        while (i in 0..<value.length - 1) {
            val a = value[i]
            val b = value[i + 1]
            if (a == b) {
                count++
                i++

                if (count >= 2)
                    return true
            }
            i++
        }

        return false
    }

    private fun find(password: String): String {
        val max = alphabetStringToNumber(("z").repeat(8))
        val start = alphabetStringToNumber(password) + 1
        for (i in start..max) {
            val value = numberToAlphabetString(i)
            if (containsStraightABC(value) && !containsIOL(value) && contains2NonOverlapPairs(value))
                return value
        }

        throw IllegalStateException("No password found")
    }

    override fun getPartA() = object : Part<String>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: String) = find(input)
    }

    override fun getPartB() = object : Part<String>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: String) = find(getPartA().solve(input).toString())
    }

}
