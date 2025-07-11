package com.nami.task.solutions.y15

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputSimplex
import com.nami.task.solutions.common.Direction
import org.joml.Vector2i

class Y15D03 : Task<List<Direction>>(2015, 3) {

    override fun getRawInputTest() = InputSimplex("^v^v^v^v^v")

    override fun getProcessedInput(raw: String) = raw.map { char ->
        when (char) {
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> throw IllegalArgumentException("Character $char is not valid")
        }
    }

    override fun getPartA() = object : Part<List<Direction>> {
        override fun solve(input: List<Direction>): Any {
            val position = Vector2i()

            val houses = mutableSetOf<Vector2i>()
            houses.add(Vector2i(position))

            input.forEach { direction ->
                position.add(direction.vector)
                houses.add(Vector2i(position))
            }

            return houses.size
        }

        override fun bonus() = 5.0
    }

    override fun getPartB() = object : Part<List<Direction>> {
        override fun solve(input: List<Direction>): Any {
            val santa = Vector2i()
            val robot = Vector2i()

            val houses = mutableSetOf<Vector2i>()
            houses.add(Vector2i())

            input.withIndex().forEach { (index, direction) ->
                val position = if (index % 2 == 0) santa else robot
                position.add(direction.vector)
                houses.add(Vector2i(position))
            }

            return houses.size
        }

        override fun bonus() = 10.0
    }

}

//fun main() = Y15D03().printResult()
fun main() = Y15D03().printVerification()
