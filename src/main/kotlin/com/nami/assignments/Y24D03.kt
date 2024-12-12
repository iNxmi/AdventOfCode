package com.nami.assignments

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y24D03 : Assignment<String>(2024, 3) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
    }

    override fun getProcessedInput(raw: String): String {
        return raw
    }

    override fun solveA(input: String): Int {
        val regex = ("mul\\(\\d+,\\d+\\)").toRegex()
        val matches = regex.findAll(input).map {
            it.value.replace("m", "").replace("u", "").replace("l", "").replace("(", "").replace(")", "")
        }.toList().map {
            Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt())
        }

        return matches.sumOf { it.first * it.second }
    }

    override fun solveB(input: String): Int {
        val regex = ("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)").toRegex()
        val matches = regex.findAll(input).map { it.value }.toList()

        val queue = mutableListOf<Pair<Int, Int>>()

        var apply = true
        for (match in matches) {
            if (match == "do()") {
                apply = true
                continue
            }

            if (match == "don't()") {
                apply = false
                continue
            }

            if (!apply)
                continue

            val clean = match.replace("m", "").replace("u", "").replace("l", "").replace("(", "").replace(")", "")
            val pair = Pair(clean.split(",")[0].toInt(), clean.split(",")[1].toInt())
            queue.add(pair)
        }

        return queue.sumOf { it.first * it.second }
    }

}