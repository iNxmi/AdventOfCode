package com.nami.assignments.y24

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y24D06 : Assignment<Y24D06.Input, Int>(2024, 6) {

    data class Input(
        val position: Pair<Int, Int>,
        val obstacles: Set<Pair<Int, Int>>,
        val worldSize: Pair<Int, Int>
    )

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent()
        )
    }

    override fun getProcessedInput(raw: String): Input {
        var position = Pair(0, 0)
        val obstacles = mutableSetOf<Pair<Int, Int>>()

        val lines = raw.lines()
        for ((y, line) in lines.withIndex())
            for ((x, c) in line.toCharArray().withIndex()) {
                if (c == '^')
                    position = Pair(x, y)

                if (c == '#')
                    obstacles.add(Pair(x, y))
            }

        val worldSize = Pair(lines[0].toCharArray().size, lines.size)

        return Input(position, obstacles, worldSize)
    }

    override fun solveA(input: Input): Int {
        var direction = Pair(0, -1)
        var positionX = input.position.first
        var positionY = input.position.second
        val visitedObstacles = mutableSetOf<Pair<Int, Int>>()

        val positionsVisited = mutableSetOf<Pair<Int, Int>>()
        positionsVisited.add(Pair(positionX, positionY))
        while (positionX in 0..<input.worldSize.first && positionY in 0..<input.worldSize.second) {
            val newPositionX = positionX + direction.first
            val newPositionY = positionY + direction.second

            if (newPositionX !in 0..<input.worldSize.first || newPositionY !in 0..<input.worldSize.second)
                break

            if (input.obstacles.contains(Pair(newPositionX, newPositionY))) {
                val newDirection = when (direction) {
                    Pair(0, -1) -> Pair(1, 0)
                    Pair(1, 0) -> Pair(0, 1)
                    Pair(0, 1) -> Pair(-1, 0)
                    Pair(-1, 0) -> Pair(0, -1)
                    else -> throw IllegalArgumentException("Unknown direction")
                }
                direction = newDirection
                visitedObstacles.add(Pair(newPositionX, newPositionY))
                continue
            }

            positionX = newPositionX
            positionY = newPositionY

            positionsVisited.add(Pair(newPositionX, newPositionY))
        }

        return positionsVisited.size
    }

    override fun solveB(input: Input): Int {
        return -1
    }

}

fun main() = println(Y24D06().solve())
