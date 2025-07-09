package com.nami.task.solutions.y16

import com.nami.println
import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputDuplex
import org.joml.Vector2i
import kotlin.math.absoluteValue

class Y16D01 : Task<List<Y16D01.Move>>(2016, 1) {

    enum class Direction { LEFT, RIGHT }

    data class Move(
        val direction: Direction,
        val distance: Int
    )

    override fun getRawInputTest() = InputDuplex(" R5, L5, R5, R3", "R8, R4, R4, R8")

    override fun getProcessedInput(raw: String): List<Move> {
        val string = raw.replace(" ", "")
        val result = string.split(",").map {
            val direction = when (it.first()) {
                'R' -> Direction.RIGHT
                'L' -> Direction.LEFT
                else -> throw IllegalStateException("Error: Unexpected direction: $it")
            }
            val distance = it.substring(1).toInt()
            Move(direction, distance)
        }
        return result
    }

    fun rotateLeft(vector: Vector2i): Vector2i {
        val result = when (vector) {
            Vector2i(0, 1) -> Vector2i(-1, 0)
            Vector2i(-1, 0) -> Vector2i(0, -1)
            Vector2i(0, -1) -> Vector2i(1, 0)
            Vector2i(1, 0) -> Vector2i(0, 1)
            else -> throw IllegalArgumentException("Cannot rotate")
        }
        return result
    }

    fun rotateRight(vector: Vector2i): Vector2i {
        val result = when (vector) {
            Vector2i(0, 1) -> Vector2i(1, 0)
            Vector2i(1, 0) -> Vector2i(0, -1)
            Vector2i(0, -1) -> Vector2i(-1, 0)
            Vector2i(-1, 0) -> Vector2i(0, 1)
            else -> throw IllegalArgumentException("Cannot rotate")
        }
        return result
    }

    override fun getPartA() = object : Part<List<Move>> {
        override fun solve(input: List<Move>): Any? {
            val position = Vector2i()
            var direction = Vector2i(0, 1)
            input.forEach { move ->
                if (move.direction == Direction.LEFT)
                    direction = rotateLeft(direction)
                else if (move.direction == Direction.RIGHT)
                    direction = rotateRight(direction)

                position.add(Vector2i(direction).mul(move.distance))
            }
            return position.x.absoluteValue + position.y.absoluteValue
        }
    }

    override fun getPartB() = object : Part<List<Move>> {
        override fun solve(input: List<Move>): Any {
            val visited = mutableSetOf<Vector2i>()
            val position = Vector2i()
            var direction = Vector2i(0, 1)
            for (move in input) {
                if (move.direction == Direction.LEFT)
                    direction = rotateLeft(direction)
                else if (move.direction == Direction.RIGHT)
                    direction = rotateRight(direction)

                for(i in 0..<move.distance) {
                    position.add(Vector2i(direction))
                    if (visited.contains(position))
                        return position.x.absoluteValue + position.y.absoluteValue

                    visited.add(Vector2i(position))
                }
            }
            return position.x.absoluteValue + position.y.absoluteValue
        }
    }

}

fun main() = Y16D01().printResult()