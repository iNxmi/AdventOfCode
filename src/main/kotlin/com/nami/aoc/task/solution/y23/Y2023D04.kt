package com.nami.aoc.task.solution.y23

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.pow

class Y2023D04 : Task<List<Y2023D04.Card>>(2023, 4) {

    data class Card(val id: Int, val winning: Set<Int>, val actual: Set<Int>) {
        fun getMatchingCount() = actual.count { it in winning }
    }

    override fun getProcessedInput(raw: String) = raw.lines().withIndex().map { (index, line) ->
        val winningLine = Regex(":(.*)\\|").find(line)!!.groupValues[1]
        val winning = Regex("\\d+").findAll(winningLine).map { it.value.toInt() }

        val actualLine = Regex("\\|(.*)").find(line)!!.groupValues[1]
        val actual = Regex("\\d+").findAll(actualLine).map { it.value.toInt() }

        Card(index + 1, winning.toSet(), actual.toSet())
    }

    override fun getPartA() = object : Part<List<Card>>(
        this, Suffix.A,
        bonus = 2.0
    ) {
        override fun solve(input: List<Card>) = input.sumOf { card ->
            val count = card.getMatchingCount()
            if (count > 0) (2.0).pow(count - 1) else 0.0
        }.toInt()
    }

    override fun getPartB() = object : Part<List<Card>>(
        this, Suffix.B,
        bonus = 10.0
    ) {
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

fun main() = Y2023D04().getVerifications().print()