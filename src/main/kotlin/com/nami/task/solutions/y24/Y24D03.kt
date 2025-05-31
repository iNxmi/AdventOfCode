package com.nami.task.solutions.y24

import com.nami.task.Task
import com.nami.task.test.TestInputSimplex

class Y24D03 : Task<String>(2024, 3) {

    override fun getRawTestInput() =
        TestInputSimplex("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

    override fun getProcessedInput(raw: String) = raw

    //TODO cleanup
    override fun solveA(input: String): Any {
        val regex = ("mul\\(\\d+,\\d+\\)").toRegex()
        val matches = regex.findAll(input).map {
            it.value
                .replace("m", "")
                .replace("u", "")
                .replace("l", "")
                .replace("(", "")
                .replace(")", "")
        }.toList().map {
            Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt())
        }

        return matches.sumOf { it.first * it.second }
    }

    override fun solveB(input: String): Any {
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

fun main() = println(Y24D03().solve())
