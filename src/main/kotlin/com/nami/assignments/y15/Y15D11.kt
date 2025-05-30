package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y15D11 : Assignment<String, String>(2015, 11) {

    private val segments = mutableSetOf<String>()

    init {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val length = 3
        for (i in 0..<alphabet.length - length + 1)
            segments.add(alphabet.substring(i, i + length))
    }

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            "ghijklmn"
        )
    }

    override fun getProcessedInput(raw: String): String = raw

    private fun numberToAlphabetString(number: Long): String {
        require(number >= 0) { "Number must be non-negative" }
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val base = alphabet.length

        var n = number
        val result = StringBuilder()

        do {
            val remainder = (n % base).toInt()
            result.append(alphabet[remainder])
            n = n / base - 1  // subtract 1 to shift counting like Excel columns
        } while (n >= 0)

        return result.reverse().toString()
    }

    private fun alphabetStringToNumber(s: String): Long {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
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
        val max = alphabetStringToNumber("z".repeat(8))
        val start = alphabetStringToNumber(password) + 1
        for (i in start..max) {
            val value = numberToAlphabetString(i)
            if (containsStraightABC(value) && !containsIOL(value) && contains2NonOverlapPairs(value))
                return value
        }

        throw IllegalStateException("No password found")
    }

    override fun solveA(input: String): String = find(input)

    override fun solveATest(input: String): String = find(input)

    override fun solveB(input: String): String = find(solveA(input))

}

fun main() = println(Y15D11().solve())
