package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.exception.AOCException
import org.joml.Vector2i

class Y2015D06 : Task<List<Y2015D06.Operation>>(2015, 6) {

    enum class Type { ON, TOGGLE, OFF }
    data class Operation(val type: Type, val start: Vector2i, val end: Vector2i)

    override fun getProcessedInput(raw: String): List<Operation> {
        val string = raw
            .replace("turn on", "on")
            .replace("turn off", "off")
            .replace(" through", "")

        val operations = mutableListOf<Operation>()
        string.lines().forEach { line ->
            val type = with(line) {
                when {
                    line.contains("on") -> Type.ON
                    line.contains("toggle") -> Type.TOGGLE
                    line.contains("off") -> Type.OFF
                    else -> throw AOCException(log, "Unexpected line $line")
                }
            }

            val split = line.split(" ")
            val strStart = split[1].trim()
            val strEnd = split[2].trim()

            val start = Vector2i(strStart.split(",")[0].toInt(), strStart.split(",")[1].toInt())
            val end = Vector2i(strEnd.split(",")[0].toInt(), strEnd.split(",")[1].toInt())

            operations.add(Operation(type, start, end))
        }

        return operations
    }

    private val size = 1000

    override fun getPartA() = object : Part<List<Operation>>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Operation>): Any {
            val lights = BooleanArray(size * size) { false }
            input.forEach { operation ->
                for (y in operation.start.y..operation.end.y)
                    for (x in operation.start.x..operation.end.x) {
                        val index = x + y * size

                        lights[index] = when (operation.type) {
                            Type.ON -> true
                            Type.TOGGLE -> !lights[index]
                            Type.OFF -> false
                        }
                    }
            }
            return lights.count { it }
        }
    }

    override fun getPartB() = object : Part<List<Operation>>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<Operation>): Any {
            val lights = IntArray(size * size) { 0 }
            input.forEach { operation ->
                for (y in operation.start.y..operation.end.y)
                    for (x in operation.start.x..operation.end.x) {
                        val index = x + y * size
                        val value = lights[index]

                        val result = when (operation.type) {
                            Type.ON -> value + 1
                            Type.TOGGLE -> value + 2
                            Type.OFF -> value - 1
                        }

                        lights[index] = if (result >= 0) result else 0
                    }
            }
            return lights.sum()
        }
    }

}

fun main() = Y2015D06().getTestVerifications().print()