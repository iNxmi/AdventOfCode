package com.nami.task.solutions.y15

import com.nami.task.Task
import com.nami.task.test.TestInputSimplex

class Y15D07 : Task<Map<String, String>>(2015, 7) {

    override fun getRawTestInput() = TestInputSimplex(
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

    private fun UShort.shl(bits: Int): UShort = this.toInt().shl(bits).toUShort()
    private fun UShort.shr(bits: Int): UShort = this.toInt().shr(bits).toUShort()

    private fun evaluate(
        states: MutableMap<String, UShort>,
        wires: Map<String, String>,
        id: String
    ): UShort {
        if (states.containsKey(id))
            return states[id]!!

        val value = wires[id] ?: throw IllegalStateException("No value for '$id'")
        val operator = extract(value, setOf('!', '&', '|', '<', '>'))

        val split = value.split(operator)
        val a = if (split[0].isEmpty()) 0u else getOrEvaluate(
            states,
            wires,
            split[0]
        ) // NOT operator has no variable in position 0 so this is a workaround
        val b = getOrEvaluate(states, wires, split[1])

        states[id] = when (operator) {
            "!" -> b.inv()
            "&" -> a and b
            "|" -> a or b
            "<" -> a.shl(b.toInt())
            ">" -> a.shr(b.toInt())
            else -> getOrEvaluate(states, wires, value)
        }
        return states[id]!!
    }

    private fun getOrEvaluate(cache: MutableMap<String, UShort>, wires: Map<String, String>, str: String): UShort =
        str.toUShortOrNull() ?: evaluate(cache, wires, str)

    private fun extract(str: String, chars: Set<Char>): String {
        chars.forEach { char ->
            if (str.contains(char))
                return char.toString()
        }

        return ""
    }

    override fun solveA(input: Map<String, String>): Any = evaluate(mutableMapOf(), input, "a").toInt()
    override fun solveATest(input: Map<String, String>): Any? = null

    override fun solveB(input: Map<String, String>): Any {
        val rewired = input.toMutableMap()
        rewired["b"] = solveA(input).toString()

        return evaluate(mutableMapOf(), rewired, "a").toInt()
    }

    override fun solveBTest(input: Map<String, String>): Any? = null

}

fun main() = println(Y15D07().solve())
