package com.nami.assignments.y24

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y24D05 : Assignment<Y24D05.ProcessedInput>(2024, 5) {

    data class ProcessedInput(val rules: Map<Int, Set<Int>>, val updates: List<List<Int>>)

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13

            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent()
        )
    }

    override fun getProcessedInput(raw: String): ProcessedInput {
        val split = raw.split(("(?m)^\\s*\$").toRegex())

        val rulesRaw = split[0].trim()
        val rules = mutableMapOf<Int, MutableSet<Int>>()
        for (rule in rulesRaw.lines()) {
            val first = rule.split("|")[0].toInt()
            val second = rule.split("|")[1].toInt()

            if (rules[first] == null)
                rules[first] = mutableSetOf()

            rules[first]!!.add(second)
        }

        val updatesRaw = split[1].trim()
        val updates = mutableListOf<List<Int>>()
        for (update in updatesRaw.lines()) updates.add(update.split(",").map { it.toInt() }.toList())

        println(rules)

        return ProcessedInput(rules, updates)
    }

    private fun isUpdateValidOld(update: List<Int>, rules: Map<Int, Set<Int>>): Int {
        val validRules = mutableSetOf(update.first())
        for (i in 0..<update.size - 1) {
            val m = update[i]
            val n = update[i + 1]

            if (!validRules.contains(m))
                return i
            if (!rules.containsKey(m))
                return i
            if (!rules[m]!!.contains(n))
                return i

            validRules.add(n)
        }

        return -1
    }

    private fun isUpdateValid(update: List<Int>, rules: Map<Int, Set<Int>>): Int {
        val validPages = mutableSetOf<Int>()

        if (!rules.containsKey(update.first()))
            return 0

        validPages.addAll(rules[update.first()]!!.toList())

        for ((i, n) in update.withIndex()) {
            if (i == 0)
                continue

            if (!validPages.contains(n))
                return i

            if (i == update.size - 1)
                continue

            if (!rules.containsKey(n))
                return i

            validPages.addAll(rules[n]!!.toList())
        }

        return -1
    }

    private fun getValidUpdates(input: ProcessedInput): Set<List<Int>> {
        val validUpdates = mutableSetOf<List<Int>>()
        for (update in input.updates)
            if (isUpdateValid(update.toList(), input.rules) == -1)
                validUpdates.add(update)

        return validUpdates
    }

    private fun getValidUpdatesOld(input: ProcessedInput): Set<List<Int>> {
        val validUpdates = mutableSetOf<List<Int>>()
        for (update in input.updates)
            if (isUpdateValidOld(update.toList(), input.rules) == -1)
                validUpdates.add(update)

        return validUpdates
    }

    private fun getInvalidUpdates(input: ProcessedInput): Set<List<Int>> {
        val validUpdates = mutableSetOf<List<Int>>()
        for (update in input.updates)
            if (isUpdateValid(update.toList(), input.rules) != -1)
                validUpdates.add(update)

        return validUpdates
    }

    private fun getSolution(updates: Set<List<Int>>): Int {
        return updates.sumOf { it[(it.size - 1) / 2] }
    }

    override fun solveA(input: ProcessedInput): Int {
        val validUpdates = getValidUpdatesOld(input)
        return getSolution(validUpdates)
    }

    override fun solveB(input: ProcessedInput): Int {
//        val invalidUpdates = getInvalidUpdates(input)
//
//        val reverseRules = mutableMapOf<Int, MutableSet<Int>>()
//        for (rule in input.rules) {
//            val key = rule.key
//            val set = rule.value
//
//            for (number in set) {
//                if (reverseRules[number] == null)
//                    reverseRules[number] = mutableSetOf()
//
//                reverseRules[number]!!.add(key)
//            }
//        }
//
//        val updates = mutableListOf<List<Int>>()
//        for (update in invalidUpdates) {
//            for (m in update) {
//                val newUpdate = mutableListOf(m)
//                for (n in update) {
//                    if (m == n)
//                        continue
//
//                    if (!reverseRules[m]!!.contains(n))
//                        break
//
//                    newUpdate.add(n)
//                }
//
//                if (newUpdate.size == update.size)
//                    updates.add(newUpdate)
//            }
//        }
//
//        return getSolution(updates.map { it.toIntArray() })
        return -1
    }

}