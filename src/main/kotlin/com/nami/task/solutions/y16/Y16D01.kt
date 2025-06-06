package com.nami.task.solutions.y16

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.input.InputSimplex
import org.joml.Vector2i

class Y16D01 : Task<List<Y16D01.Move>>(2016, 1) {

    enum class Direction { LEFT, RIGHT }

    data class Move(
        val direction: Direction,
        val distance: Int
    )

    override fun getRawInputTest() = InputSimplex(
        """
        R5, L5, R5, R3
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String): List<Move> {
        val string = raw.replace(" ", "")
        val result = string.split(",").map {
            val direction = when (it.first()) {
                'R' -> Direction.RIGHT
                'L' -> Direction.LEFT
                else -> throw IllegalStateException("Error: Unexpected direction: $it")
            }
            val distance = it.last().digitToInt()
            Move(direction, distance)
        }
        return result
    }

    override fun getSubTaskA() = object : SubTask<List<Move>> {
        override fun solve(input: List<Move>): Any? {
            val position = Vector2i()
            val direction = 0
            input.forEach { debug(it) }
            return null
        }
    }

    override fun getSubTaskB() = object : SubTask<List<Move>> {
        override fun solve(input: List<Move>) = null
    }

}

fun main() = Y16D01().getResult().println()