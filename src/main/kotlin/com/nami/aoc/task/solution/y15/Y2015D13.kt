package com.nami.aoc.task.solution.y15

import com.nami.aoc.permutations
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D13 : Task<Y2015D13.Input>(2015, 13) {

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

    override fun getPartA() = object : Part<Input>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: Input): Any {
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
    }

    override fun getPartB() = object : Part<Input>(
        this, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: Input): Any {
            val permutations = input.people.toList().permutations()

            val sums = mutableSetOf<Int>()
            for (list in permutations) {
                for (i in list.indices) {
                    var sum = 0
                    for (j in list.indices) {
                        if (i == j)
                            continue

                        val personA = list[j]
                        val personB = list[(j + 1) % list.size]

                        val ruleA = Pair(personA, personB)
                        val happinessA = input.rules[ruleA] ?: 0

                        val ruleB = Pair(personB, personA)
                        val happinessB = input.rules[ruleB] ?: 0

                        sum += happinessA + happinessB
                    }
                    sums.add(sum)
                }
            }

            return sums.max()
        }
    }

}