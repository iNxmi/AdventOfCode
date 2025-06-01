package com.nami.task.solutions.y15

import com.nami.permutations
import com.nami.println
import com.nami.task.Task
import com.nami.task.test.TestInput
import com.nami.task.test.TestInputSimplex

class Y15D13 : Task<Y15D13.Input>(2015, 13) {

    override fun getRawTestInput(): TestInput = TestInputSimplex(
        """
            Alice would gain 54 happiness units by sitting next to Bob.
            Alice would lose 79 happiness units by sitting next to Carol.
            Alice would lose 2 happiness units by sitting next to David.
            Bob would gain 83 happiness units by sitting next to Alice.
            Bob would lose 7 happiness units by sitting next to Carol.
            Bob would lose 63 happiness units by sitting next to David.
            Carol would lose 62 happiness units by sitting next to Alice.
            Carol would gain 60 happiness units by sitting next to Bob.
            Carol would gain 55 happiness units by sitting next to David.
            David would gain 46 happiness units by sitting next to Alice.
            David would lose 7 happiness units by sitting next to Bob.
            David would gain 41 happiness units by sitting next to Carol.
        """.trimIndent()
    )

    data class Input(
        val people: Set<String>,
        val rules: Map<Pair<String, String>, Int>
    )

    override fun getProcessedInput(raw: String): Input {
        val string = raw
            .replace("gain", "")
            .replace("lose", "-")
            .replace("would", ",")
            .replace("happiness units by sitting next to", ",")
            .replace(".", "")
            .replace(" ", "")

        val people = mutableSetOf<String>()
        val rules = mutableMapOf<Pair<String, String>, Int>()
        string.lines().forEach { line ->
            val split = line.split(",")
            val personA = split[0]
            val personB = split[2]
            val happiness = split[1].toInt()

            people.add(personA)
            people.add(personB)

            rules[Pair(personA, personB)] = happiness
        }

        return Input(people, rules)
    }

    override fun solveA(input: Input): Any {
        val permutations = input.people.toList().permutations()

        val sums = mutableSetOf<Int>()
        for (list in permutations) {
            var sum = 0
            for (i in list.indices) {
                val personA = list[i]
                val personB = list[(i + 1) % list.size]

                val ruleA = Pair(personA, personB)
                val happinessA = input.rules[ruleA] ?: throw IllegalStateException("no  rule '$ruleA'")

                val ruleB = Pair(personB, personA)
                val happinessB = input.rules[ruleB] ?: throw IllegalStateException("no  rule '$ruleB'")

                sum += happinessA + happinessB
            }
            sums.add(sum)
        }

        return sums.max()
    }

    override fun solveB(input: Input): Any {
        val newSet = input.people.toMutableSet()
        newSet.add("Me")
        val permutations = newSet.toList().permutations()
        permutations.size.println()

        val sums = mutableSetOf<Int>()
        for (list in permutations) {
            var sum = 0
            for (i in list.indices) {
                val personA = list[i]
                val personB = list[(i + 1) % list.size]

                val ruleA = Pair(personA, personB)
                val happinessA = input.rules[ruleA] ?: 0

                val ruleB = Pair(personB, personA)
                val happinessB = input.rules[ruleB] ?: 0

                sum += happinessA + happinessB
            }
            sums.add(sum)
        }

        return sums.max()
    }

}

fun main() = Y15D13().solve().println()
