package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.input.InputDuplex

class Y15D05 : Task<List<String>>(2015, 5) {

    override fun getRawInputTest() = InputDuplex(
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

    override fun getProcessedInput(raw: String) = raw.lines()

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')
    private fun containsThreeVowels(string: String): Boolean {
        var count = 0
        vowels.forEach { v ->
            count += string.count { it == v }
        }

        return count >= 3
    }

    private val alphabet = ("abcdefghijklmnopqrstuvwxyz").toCharArray()
    private fun containsOneLetterTwice(string: String): Boolean {
        alphabet.forEach { c ->
            val sequence = "$c$c"
            if (string.contains(sequence))
                return true
        }

        return false
    }

    private val specifics = setOf("ab", "cd", "pq", "xy")
    private fun containsSpecifics(string: String): Boolean {
        specifics.forEach {
            if (string.contains(it))
                return true
        }

        return false
    }

    private fun containsTwoLetterPair(string: String): Boolean {
        for (x in 1..<string.length) {
            val x0 = x - 1
            val sequenceA = string.subSequence(x0..x)

            for (y in 1..<string.length) {
                val y0 = y - 1
                if (y0 == x0 || y0 == x || y == x0 || y == x)
                    continue

                val sequenceB = string.subSequence(y0..y)
                if (sequenceA == sequenceB)
                    return true
            }
        }

        return false
    }

    private fun containsLetterTwiceWithSpace(string: String): Boolean {
        for (x in 0..<string.length - 2) {
            val letterA = string[x]
            val letterB = string[x + 2]
            if (letterA == letterB)
                return true
        }

        return false
    }

    override fun getSubTaskA() = object : SubTask<List<String>> {
        override fun solve(input: List<String>) =
            input.count { containsThreeVowels(it) && containsOneLetterTwice(it) && !containsSpecifics(it) }
        override fun bonus() = 5.0
    }

    override fun getSubTaskB() = object : SubTask<List<String>> {
        override fun solve(input: List<String>) =
            input.count { containsTwoLetterPair(it) && containsLetterTwiceWithSpace(it) }
        override fun bonus() = 10.0
    }

}

fun main() = Y15D05().getResult().println()
