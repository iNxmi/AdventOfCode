package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.common.Direction
import com.nami.aoc.task.solution.exception.AOCException
import org.joml.Vector2i

class Y2015D03 : Task<List<Direction>>(2015, 3) {

    override fun getProcessedInput(raw: String) = raw.map { char ->
        when (char) {
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> throw AOCException(log, "Character '$char' is not valid")
        }
    }

    override fun getPartA() = object : Part<List<Direction>>(
        this, Suffix.A,
        bonus = 5.0
    ) {
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
    }

    override fun getPartB() = object : Part<List<Direction>>(
        this, Suffix.B,
        bonus = 10.0
    ) {
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
    }

}
