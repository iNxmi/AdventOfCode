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

    private fun evaluate(cache: MutableMap<String, Int>, wires: Map<String, String>, depth: Int, id: String): Int {
        println("-".repeat(depth) + id)

        if (cache.containsKey(id))
            return cache[id]!!

        val value = wires[id] ?: throw IllegalStateException("no value for $id")
        if (value.toIntOrNull() != null) {
            val result = wires[id]!!.toInt()
            cache[id] = result
            return result
        }

        if (value.contains('!')) {
            val index = value.indexOf('!')
            val a = value.subSequence(index + 1..<value.length).toString()

            val result = (if (a.toIntOrNull() == null) evaluate(cache, wires, depth + 1, a) else a.toInt()).inv()

            cache[id] = result
            return result
        } else if (value.contains('&')) {
            val index = value.indexOf('&')
            val a = value.subSequence(0..<index).toString()
            val b = value.subSequence(index + 1..<value.length).toString()

            val result =
                (if (a.toIntOrNull() == null) evaluate(
                    cache,
                    wires, depth + 1,
                    a
                ) else a.toInt()) and (if (b.toIntOrNull() == null) evaluate(cache, wires, depth + 1, b) else b.toInt())

            cache[id] = result
            return result
        } else if (value.contains('|')) {
            val index = value.indexOf('|')
            val a = value.subSequence(0..<index).toString()
            val b = value.subSequence(index + 1..<value.length).toString()

            val result =
                (if (a.toIntOrNull() == null) evaluate(
                    cache,
                    wires, depth + 1,
                    a
                ) else a.toInt()) or (if (b.toIntOrNull() == null) evaluate(cache, wires, depth + 1, b) else b.toInt())

            cache[id] = result
            return result
        } else if (value.contains('<')) {
            val index = value.indexOf('<')
            val a = value.subSequence(0..<index).toString()
            val b = value.subSequence(index + 1..<value.length).toString()

            val result =
                (if (a.toIntOrNull() == null) evaluate(
                    cache,
                    wires, depth + 1,
                    a
                ) else a.toInt()) shl (if (b.toIntOrNull() == null) evaluate(cache, wires, depth + 1, b) else b.toInt())

            cache[id] = result
            return result
        } else if (value.contains('>')) {
            val index = value.indexOf('>')
            val a = value.subSequence(0..<index).toString()
            val b = value.subSequence(index + 1..<value.length).toString()

            val result =
                (if (a.toIntOrNull() == null) evaluate(
                    cache,
                    wires, depth + 1,
                    a
                ) else a.toInt()) ushr (if (b.toIntOrNull() == null) evaluate(
                    cache,
                    wires,
                    depth + 1,
                    b
                ) else b.toInt())

            cache[id] = result
            return result
        }

        val result = evaluate(cache, wires, depth + 1, value)
        cache[id] = result
        return result
    }

    override fun solveA(input: Map<String, String>): Number {
        return evaluate(mutableMapOf(), input, 0, "a")
    }

    override fun solveATest(input: Map<String, String>): Number {
        return -1
    }

    override fun solveB(input: Map<String, String>): Number {
        val rewired = input.toMutableMap()
        rewired["b"] = evaluate(mutableMapOf(), input, 0, "a").toString()

        return evaluate(mutableMapOf(), rewired, 0, "a")
    }

    override fun solveBTest(input: Map<String, String>): Number {
        return -1
    }

}