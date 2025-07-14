package com.nami.aoc.task.solutions.y23

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex
import org.apache.commons.math3.util.ArithmeticUtils.pow

class Y23D04 : Task<List<Y23D04.Card>>(2023, 4) {

    data class Card(val id: Int, val winning: Set<Int>, val actual: Set<Int>) {
        fun getMatchingCount() = actual.count { it in winning }
    }

    override fun getRawInputTest() = InputSimplex(
        """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String) = raw.lines().withIndex().map { (index, line) ->
        val winningLine = Regex(":(.*)\\|").find(line)!!.groupValues[1]
        val winning = Regex("\\d+").findAll(winningLine).map { it.value.toInt() }

        val actualLine = Regex("\\|(.*)").find(line)!!.groupValues[1]
        val actual = Regex("\\d+").findAll(actualLine).map { it.value.toInt() }

        Card(index + 1, winning.toSet(), actual.toSet())
    }

    override fun getPartA() = object : Part<List<Card>> {
        override fun solve(input: List<Card>) = input.sumOf { card ->
            val count = card.getMatchingCount()
            if (count > 0) pow(2, count - 1) else 0
        }

        override fun bonus() = 2.0
    }

    override fun getPartB() = object : Part<List<Card>> {
        override fun solve(input: List<Card>): Any {
            val cards = input.associate { it.id to 1 }.toMutableMap()
            input.forEach { card ->
                val countWinning = card.getMatchingCount()
                val countCards = cards.getOrDefault(card.id, 0)
                for (i in 1..countWinning)
                    cards[card.id + i] = cards.getOrDefault(card.id + i, 0) + countCards
            }

            return cards.map { it.value }.sum()
        }
    }

}

fun main() = Y23D04().printResults()
//fun main() = Y23D04().printVerifications()
