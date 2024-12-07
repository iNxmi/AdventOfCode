package com.nami.assignments

import com.nami.Assignment

class Y24D05 : Assignment<Y24D05.ProcessedInput>(2024, 5) {

    data class ProcessedInput(val rules: Map<Int, Set<Int>>, val updates: List<IntArray>)

    override fun getProcessedInput(raw: String): ProcessedInput {
        val split = raw.split(("(?m)^\\s*\$").toRegex())

        val rulesRaw = split[0].trim()
        val rules = mutableMapOf<Int, MutableSet<Int>>()
        for (rule in rulesRaw.lines()) {
            val first = rule.split("|")[0].toInt()
            val second = rule.split("|")[1].toInt()

            if (rules[first] == null) rules[first] = mutableSetOf()

            rules[first]!!.add(second)
        }

        val updatesRaw = split[1].trim()
        val updates = mutableListOf<IntArray>()
        for (update in updatesRaw.lines()) updates.add(update.split(",").map { it.toInt() }.toIntArray())

        return ProcessedInput(rules, updates)
    }

    private fun isUpdateValid(update: IntArray, rules: Map<Int, Set<Int>>): Int {
        val validRules = mutableSetOf(update.first())
        for (i in 0..<update.size - 1) {
            val m = update[i]
            val n = update[i + 1]

            println("A")
            if (!validRules.contains(m)) return i
            println("B")
            if (!rules.containsKey(m)) return i
            println("C")
            if (!rules[m]!!.contains(n)) return i
            println("D")
            validRules.add(n)
        }

        return -1
    }

    private fun getValidUpdates(input: ProcessedInput): List<IntArray> {
        val validUpdates = mutableListOf<IntArray>()
        for (update in input.updates) if (isUpdateValid(update, input.rules) == -1) validUpdates.add(update)

        return validUpdates
    }

    private fun getInvalidUpdates(input: ProcessedInput): List<IntArray> {
        val validUpdates = mutableListOf<IntArray>()
        for (update in input.updates) if (isUpdateValid(update, input.rules) != -1) validUpdates.add(update)

        return validUpdates
    }

    private fun getSolution(updates: List<IntArray>): Int {
        return updates.sumOf { it[(it.size - 1) / 2] }
    }

    override fun solveA(input: ProcessedInput): Int {
        val validUpdates = getValidUpdates(input)
        return getSolution(validUpdates)
    }

    override fun solveB(input: ProcessedInput): Int {
        val invalidUpdates = getInvalidUpdates(input)

        val reverseRules = mutableMapOf<Int, MutableSet<Int>>()
        for (rule in input.rules) {
            val key = rule.key
            val set = rule.value

            for (number in set) {
                if (reverseRules[number] == null)
                    reverseRules[number] = mutableSetOf()

                reverseRules[number]!!.add(key)
            }
        }

        val updates = mutableListOf<List<Int>>()
        for (update in invalidUpdates) {
            for (m in update) {
                val newUpdate = mutableListOf(m)
                for (n in update) {
                    if (m == n)
                        continue

                    if (!reverseRules[m]!!.contains(n))
                        break

                    newUpdate.add(n)
                }

                if (newUpdate.size == update.size)
                    updates.add(newUpdate)
            }
        }

        return getSolution(updates.map { it.toIntArray() })
    }

}