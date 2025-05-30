package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import org.joml.Vector2i

class Y15D06 : Assignment<List<Y15D06.Operation>, Int>(2015, 6) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            turn on 0,0 through 999,999
            toggle 0,0 through 999,0
            turn off 499,499 through 500,500
            """.trimIndent()
        )
    }

    enum class Task {
        ON,
        TOGGLE,
        OFF
    }

    data class Operation(val task: Task, val start: Vector2i, val end: Vector2i)

    override fun getProcessedInput(raw: String): List<Operation> {
        val str = raw
            .replace("turn on", "on")
            .replace("turn off", "off")
            .replace(" through", "")

        val operations = mutableListOf<Operation>()
        str.lines().forEach { line ->
            val task: Task
            if (line.contains("on"))
                task = Task.ON
            else if (line.contains("toggle"))
                task = Task.TOGGLE
            else if (line.contains("off"))
                task = Task.OFF
            else throw IllegalStateException("Unexpected line $line")

            val split = line.split(" ")
            val strStart = split[1].trim()
            val strEnd = split[2].trim()

            val start = Vector2i(strStart.split(",")[0].toInt(), strStart.split(",")[1].toInt())
            val end = Vector2i(strEnd.split(",")[0].toInt(), strEnd.split(",")[1].toInt())

            operations.add(Operation(task, start, end))
        }

        return operations
    }

    private val size = 1000

    override fun solveA(input: List<Operation>): Int {
        val lights = BooleanArray(size * size) { false }
        input.forEach { operation ->
            for (y in operation.start.y..operation.end.y)
                for (x in operation.start.x..operation.end.x) {
                    val index = x + y * size

                    lights[index] = when (operation.task) {
                        Task.ON -> true
                        Task.TOGGLE -> !lights[index]
                        Task.OFF -> false
                    }
                }
        }
        return lights.count { it }
    }

    override fun solveB(input: List<Operation>): Int {
        val lights = IntArray(size * size) { 0 }
        input.forEach { operation ->
            for (y in operation.start.y..operation.end.y)
                for (x in operation.start.x..operation.end.x) {
                    val index = x + y * size
                    val value = lights[index]

                    val result = when (operation.task) {
                        Task.ON -> value + 1
                        Task.TOGGLE -> value + 2
                        Task.OFF -> value - 1
                    }

                    lights[index] = if (result >= 0) result else 0
                }
        }
        return lights.sum()
    }

}

fun main() = println(Y15D06().solve())
