package com.nami.aoc.task.solution.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2024D05 : Task<Y2024D05.Input>(2024, 5) {

    data class Input(val rules: Map<Int, Set<Int>>, val updates: List<List<Int>>)

    override fun getProcessedInput(raw: String): Input {
        val split = raw.split(("(?m)^\\s*$").toRegex())

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
        for (update in updatesRaw.lines())
            updates.add(update.split(",").map { it.toInt() }.toList())

        return Input(rules, updates)
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

    private fun getValidUpdates(input: Input): Set<List<Int>> {
        val validUpdates = mutableSetOf<List<Int>>()
        for (update in input.updates)
            if (isUpdateValid(update.toList(), input.rules) == -1)
                validUpdates.add(update)

        return validUpdates
    }

    private fun getValidUpdatesOld(input: Input): Set<List<Int>> {
        val validUpdates = mutableSetOf<List<Int>>()
        for (update in input.updates)
            if (isUpdateValidOld(update.toList(), input.rules) == -1)
                validUpdates.add(update)

        return validUpdates
    }

    private fun getInvalidUpdates(input: Input): Set<List<Int>> {
        val validUpdates = mutableSetOf<List<Int>>()
        for (update in input.updates)
            if (isUpdateValid(update.toList(), input.rules) != -1)
                validUpdates.add(update)

        return validUpdates
    }

    private fun getSolution(updates: Set<List<Int>>) = updates.sumOf { it[(it.size - 1) / 2] }

    override fun getPartA() = object : Part<Input>(
        this,Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: Input): Any {
            val validUpdates = getValidUpdatesOld(input)
            return getSolution(validUpdates)
        }
    }

    override fun getPartB() = object : Part<Input>(
        this,Suffix.B
    ) {
        override fun solve(input: Input): Any? {
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
            return null
        }
    }

}