package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D05 : Task<List<String>>(2015, 5) {

    override fun getProcessedInput(raw: String) = raw.lines()

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')
    private fun containsThreeVowels(string: String) = vowels.sumOf { vowel -> string.count { it == vowel } } >= 3

    private val alphabet = ("abcdefghijklmnopqrstuvwxyz").toCharArray()
    private fun containsOneLetterTwice(string: String) = alphabet.any { string.contains("$it$it") }

    private val specifics = setOf("ab", "cd", "pq", "xy")
    private fun containsSpecifics(string: String) = specifics.any { string.contains(it) }

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

    override fun getPartA() = object : Part<List<String>>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<String>) =
            input.count { containsThreeVowels(it) && containsOneLetterTwice(it) && !containsSpecifics(it) }
    }

    override fun getPartB() = object : Part<List<String>>(
        this, Type.B,
        bonus = 10.0
    )  {
        override fun solve(input: List<String>) =
            input.count { containsTwoLetterPair(it) && containsLetterTwiceWithSpace(it) }
    }

}