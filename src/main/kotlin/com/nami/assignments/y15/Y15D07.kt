package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y15D07 : Assignment<Map<String, String>>(2015, 7) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
            """.trimIndent()
        )
    }

    override fun getProcessedInput(raw: String): Map<String, String> {
        val str = raw
            .replace("NOT", "!")
            .replace("AND", "&")
            .replace("OR", "|")
            .replace("LSHIFT", "<")
            .replace("RSHIFT", ">")
            .replace("->", "=")
            .replace(" ", "")

        val wires = mutableMapOf<String, String>()
        str.lines().forEach { line ->
            val split = line.split("=")
            val id = split[1]
            val value = split[0]
            wires[id] = value
        }

        return wires
    }

    private fun evaluate(
        cache: MutableMap<String, Int>,
        wires: Map<String, String>,
        id: String
    ): Int {
        if (cache.containsKey(id))
            return cache[id]!!


        val value = wires[id] ?: throw IllegalStateException("No value for '$id'")
        if (value.toIntOrNull() != null) {
            val result = value.toInt()
            cache[id] = result
        } else if (value.contains('!')) {
            val a = value.replace("!", "")

            val result = getOrEvaluate(cache, wires, a).inv()
            cache[id] = result
        } else if (value.contains('&')) {
            val split = value.split('&')
            val a = split[0]
            val b = split[1]

            val result = getOrEvaluate(cache, wires, a) and getOrEvaluate(cache, wires, b)
            cache[id] = result
        } else if (value.contains('|')) {
            val split = value.split('|')
            val a = split[0]
            val b = split[1]

            val result = getOrEvaluate(cache, wires, a) or getOrEvaluate(cache, wires, b)
            cache[id] = result
        } else if (value.contains('<')) {
            val split = value.split('<')
            val a = split[0]
            val b = split[1]

            val result = getOrEvaluate(cache, wires, a) shl getOrEvaluate(cache, wires, b)
            cache[id] = result
        } else if (value.contains('>')) {
            val split = value.split('>')
            val a = split[0]
            val b = split[1]

            val result = getOrEvaluate(cache, wires, a) ushr getOrEvaluate(cache, wires, b)
            cache[id] = result
        } else {
            val result = evaluate(cache, wires, value)
            cache[id] = result
        }

//        val operator = extract(value, setOf("!", "&", "|", "<", ">"))
//        val result = when (operator) {
//            "!" -> {}
//            "&" -> {}
//            "|" -> {}
//            "<" -> {}
//            ">" -> {}
//            else -> evaluate(cache, wires, value)
//        }

        return cache[id]!!
    }

    private fun getOrEvaluate(cache: MutableMap<String, Int>, wires: Map<String, String>, str: String): Int {
        return str.toIntOrNull() ?: evaluate(cache, wires, str)
    }

    private fun extract(str: String, chars: Set<String>): String {
        chars.forEach { char ->
            if (str.contains(char))
                return char
        }

        return ""
    }

    override fun solveA(input: Map<String, String>): Number {
        return evaluate(mutableMapOf(), input, "a")
    }

    override fun solveATest(input: Map<String, String>): Number {
        return -1
    }

    override fun solveB(input: Map<String, String>): Number {
        val rewired = input.toMutableMap()
        rewired["b"] = evaluate(mutableMapOf(), input, "a").toString()

        return evaluate(mutableMapOf(), rewired, "a")
    }

    override fun solveBTest(input: Map<String, String>): Number {
        return -1
    }

}