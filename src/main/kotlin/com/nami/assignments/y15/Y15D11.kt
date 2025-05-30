package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import java.lang.Math.pow

class Y15D11 : Assignment<String>(2015, 11) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            "ghijklmn"
        )
    }

    override fun getProcessedInput(raw: String): String = raw

    private fun convert(number: String, fromAlphabet: String, toAlphabet: String): String {
        val fromBase = fromAlphabet.length
        val toBase = toAlphabet.length

        // Step 1: Convert input number (string) to decimal (assuming fromAlphabet is base-X digits)
        var decimal = 0L
        for (char in number) {
            val digit = fromAlphabet.indexOf(char)
            require(digit >= 0) { "Invalid character '$char' in input" }
            decimal = decimal * fromBase + digit
        }

        // Excel-style 1-based base conversion must start from 1
        decimal += 1

        // Step 2: Convert decimal to Excel-style base (1-based logic)
        val result = StringBuilder()
        var n = decimal
        while (n > 0) {
            n-- // shift to 0-based
            val digit = (n % toBase).toInt()
            result.append(toAlphabet[digit])
            n /= toBase
        }

        return result.reverse().toString()
    }

    fun numberToAlphabetString(number: Long): String {
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

    fun alphabetStringToNumber(s: String): Long {
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

    private val alphabetBase26 = "abcdefghijklmnopqrstuvwxyz"
    private val alphabetBase10 = "0123456789"
    override fun solveA(input: String): Number {
        for (i in 0..20000) {
            val text = numberToAlphabetString(i.toLong())
            val number = alphabetStringToNumber(text)
            println("$i $number $text")
        }

        println(input)
        println(alphabetStringToNumber(input))
        println(alphabetStringToNumber("z".repeat(8)))
        return -1
    }

    override fun solveB(input: String): Number {
        return -1
    }

    override fun solveBTest(input: String): Number = -1

}

fun main() {
    println(Y15D11().solve())
}