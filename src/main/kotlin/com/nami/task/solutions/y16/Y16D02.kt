package com.nami.task.solutions.y16

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputSimplex
import com.nami.task.solutions.common.Direction
import org.joml.Vector2i
import kotlin.math.absoluteValue

class Y16D02 : Task<List<List<Direction>>>(2016, 2) {

    override fun getRawInputTest() = InputSimplex(
        """
        ULL
        RRDDD
        LURDL
        UUUUD
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        line.toCharArray().map { char ->
            when (char) {
                'U' -> Direction.UP
                'D' -> Direction.DOWN
                'L' -> Direction.LEFT
                'R' -> Direction.RIGHT
                else -> throw IllegalArgumentException("Character '$char' is illegal")
            }
        }
    }

    override fun getPartA() = object : Part<List<List<Direction>>> {

        val keypad = mapOf(
            Vector2i(-1, 1) to "1",
            Vector2i(0, 1) to "2",
            Vector2i(1, 1) to "3",
            Vector2i(-1, 0) to "4",
            Vector2i(0, 0) to "5",
            Vector2i(1, 0) to "6",
            Vector2i(-1, -1) to "7",
            Vector2i(0, -1) to "8",
            Vector2i(1, -1) to "9"
        )

        override fun solve(input: List<List<Direction>>): Any {
            val sb = StringBuilder()

            val position = Vector2i()
            for (directions in input) {
                for (direction in directions) {
                    position.add(direction.vector)
                    position.x = position.x.coerceIn(-1, 1)
                    position.y = position.y.coerceIn(-1, 1)
                }

                sb.append(keypad[position]!!)
            }

            return sb.toString()
        }

    }

    override fun getPartB() = object : Part<List<List<Direction>>> {

        val keypad = mapOf(
            Vector2i(0, 2) to "1",
            Vector2i(-1, 1) to "2",
            Vector2i(0, 1) to "3",
            Vector2i(1, 1) to "4",
            Vector2i(-2, 0) to "5",
            Vector2i(-1, 0) to "6",
            Vector2i(0, 0) to "7",
            Vector2i(1, 0) to "8",
            Vector2i(2, 0) to "9",
            Vector2i(-1, -1) to "A",
            Vector2i(0, -1) to "B",
            Vector2i(1, -1) to "C",
            Vector2i(0, -2) to "D"
        )

        override fun solve(input: List<List<Direction>>): Any {
            val sb = StringBuilder()

            // We start at position 5 on the keypad
            val position = Vector2i(-2, 0)

            for (directions in input) {
                for (direction in directions) {
                    val temp = Vector2i(position).add(direction.vector)
                    if (temp.x.absoluteValue + temp.y.absoluteValue <= 2)
                        position.add(direction.vector)
                }

                sb.append(keypad[position]!!)
            }

            return sb.toString()
        }

    }

}

fun main() = Y16D02().printResult()
//fun main() = Y16D02().printVerification()