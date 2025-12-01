package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2025D01 : Task<List<Y2025D01.Rotation>>(2025, 1) {

    companion object {
        private val DIAL_START = 50
        private val DIAL_SIZE = 100
    }

    enum class Direction {
        LEFT, RIGHT
    }

    data class Rotation(
        val direction: Direction,
        val amount: Int
    ) {

        fun calculate(position: Int) = when (direction) {
            Direction.LEFT -> position - amount
            Direction.RIGHT -> position + amount
        } % DIAL_SIZE

    }

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val direction = if (line[0] == 'R') Direction.RIGHT else Direction.LEFT
        val amount = line.substring(1).toInt()
        Rotation(direction, amount)
    }

    override fun getPartA() = object : Part<List<Rotation>>(
        this, Suffix.A
    ) {
        override fun solve(input: List<Rotation>): Int {
            var count = 0
            var position = 50
            for (rotation in input) {
                position = rotation.calculate(position)

                if(position == 0)
                    count++
            }

            return count
        }
    }

    override fun getPartB() = object : Part<List<Rotation>>(
        this, Suffix.B
    ) {
        override fun solve(input: List<Rotation>): Int {
            var count = 0
            var position = 50
            for (rotation in input) {
                if(rotation.direction == Direction.RIGHT) {
                    for(i in 0..<rotation.amount) {
                        position += 1
                        position = position.mod(DIAL_SIZE)

                        if (position == 0)
                            count++

                        println(position)
                    }
                } else {
                    for(i in 0..<rotation.amount) {
                        position -= 1
                        position = position.mod(DIAL_SIZE)

                        if (position == 0)
                            count++

                        println(position)
                    }
                }
            }

            return count
        }
    }

}

//fun main() = Y2025D01().getTestVerifications().print()
//fun main() = Y2025D01().getResults().print()
fun main() = Y2025D01().getVerifications().print()