package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputDuplex

class Y15D05 : Assignment<List<String>>(2015, 5) {

    override fun getRawTestInput(): TestInput {
        return TestInputDuplex(
            """
            ugknbfddgicrmopn
            aaa
            jchzalrnumimnmhp
            haegwjzuvuyypxyu
            dvszwmarrgswjxmb
            """.trimIndent(),
            """
            qjhvhtzxzqqjkmpb
            xxyxx
            uurcxstgmygtbstg
            ieodomkazucvgmuy
            """.trimIndent(),
        )
    }

    override fun getProcessedInput(raw: String): List<String> {
        return raw.lines()
    }

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')
    private fun containsThreeVowels(str: String): Boolean {
        var count = 0
        vowels.forEach { v ->
            count += str.count { it == v }
        }

        return count >= 3
    }

    private val alphabet = ("abcdefghijklmnopqrstuvwxyz").toCharArray()
    private fun containsOneLetterTwice(str: String): Boolean {
        alphabet.forEach { c ->
            val sequence = "$c$c"
            if (str.contains(sequence))
                return true
        }

        return false
    }

    private val specifics = setOf("ab", "cd", "pq", "xy")
    private fun containsSpecifics(str: String): Boolean {
        specifics.forEach {
            if (str.contains(it))
                return true
        }

        return false
    }

    override fun solveA(input: List<String>): Int {
        return input.count { containsThreeVowels(it) && containsOneLetterTwice(it) && !containsSpecifics(it) }
    }

    private fun containsTwoLetterPair(str: String): Boolean {
        for (x in 1..<str.length) {
            val x0 = x - 1
            val sequenceA = str.subSequence(x0..x)

            for (y in 1..<str.length) {
                val y0 = y - 1
                if (y0 == x0 || y0 == x || y == x0 || y == x)
                    continue

                val sequenceB = str.subSequence(y0..y)
                if (sequenceA == sequenceB)
                    return true
            }
        }

        return false
    }

    private fun containsLetterTwiceWithSpace(str: String): Boolean {
        for (x in 0..<str.length - 2) {
            val letterA = str[x]
            val letterB = str[x + 2]
            if (letterA == letterB)
                return true
        }

        return false
    }

    override fun solveB(input: List<String>): Int {
        return input.count { containsTwoLetterPair(it) && containsLetterTwiceWithSpace(it) }
    }

}

fun main() {
    println(Y15D05().solve())
}