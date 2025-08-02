package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D07 : Task<Map<String, String>>(2015, 7) {

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

    override fun getPartA() = object : Part<Map<String, String>>(
        year, day, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: Map<String, String>) = evaluate(mutableMapOf(), input, "a").toInt()
    }

    override fun getPartB() = object : Part<Map<String, String>>(
        year, day, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: Map<String, String>): Any {
            val rewired = input.toMutableMap()
            rewired["b"] = getPartA().solve(input).toString()

            return evaluate(mutableMapOf(), rewired, "a").toInt()
        }
    }

}
