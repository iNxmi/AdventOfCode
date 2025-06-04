package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.test.TestInputSimplex

class Y15D07 : Task<Map<String, String>>(2015, 7) {

    override fun getRawInputTest() = TestInputSimplex(
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
        val string = raw
            .replace("NOT", "!")
            .replace("AND", "&")
            .replace("OR", "|")
            .replace("LSHIFT", "<")
            .replace("RSHIFT", ">")
            .replace("->", "=")
            .replace(" ", "")

        val wires = mutableMapOf<String, String>()
        string.lines().forEach { line ->
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

    private fun getOrEvaluate(cache: MutableMap<String, UShort>, wires: Map<String, String>, string: String): UShort =
        string.toUShortOrNull() ?: evaluate(cache, wires, string)

    private fun extract(string: String, chars: Set<Char>): String {
        chars.forEach { char ->
            if (string.contains(char))
                return char.toString()
        }

        return ""
    }

    override fun getSubTaskA() = object : SubTask<Map<String, String>> {
        override fun solve(input: Map<String, String>) = evaluate(mutableMapOf(), input, "a").toInt()
        override fun test(input: Map<String, String>) = null
    }

    override fun getSubTaskB() = object : SubTask<Map<String, String>> {
        override fun solve(input: Map<String, String>): Any {
            val rewired = input.toMutableMap()
            rewired["b"] = getSubTaskA().solve(input).toString()

            return evaluate(mutableMapOf(), rewired, "a").toInt()
        }

        override fun test(input: Map<String, String>) = null
    }

}

fun main() = Y15D07().getResult().println()
