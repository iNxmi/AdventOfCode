package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2025D01 : Task<List<Y2025D01.Rotation>>(2025, 1) {

    companion object {
        private const val DIAL_START = 50
        private const val DIAL_SIZE = 100
    }

    enum class Direction(val sign: Int) {
        LEFT(-1),
        RIGHT(+1)
    }

    data class Rotation(
        val direction: Direction,
        val amount: Int
    ) {
        val numeric = amount * direction.sign
    }

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val direction = if (line[0] == 'R') Direction.RIGHT else Direction.LEFT
        val amount = line.substring(1).toInt()
        Rotation(direction, amount)
    }

    override fun getPartA() = object : Part<List<Rotation>>(this, Suffix.A) {
        override fun solve(input: List<Rotation>): Int {
            var position = DIAL_START
            return input.count {
                position = (position + it.numeric).mod(DIAL_SIZE)
                position == 0
            }
        }
    }

    override fun getPartB() = object : Part<List<Rotation>>(this, Suffix.B) {
        override fun solve(input: List<Rotation>): Int {
            var position = DIAL_START
            return input.sumOf { rotation ->
                (0..<rotation.amount).count { _ ->
                    position += rotation.direction.sign
                    position = position.mod(DIAL_SIZE)

                    position == 0
                }
            }
        }
    }

}

//fun main() = Y2025D01().getTestVerifications().print()
//fun main() = Y2025D01().getResults().print()
fun main() = Y2025D01().getVerifications().print()