package com.nami.assignments.y24

import com.nami.Assignment
import com.nami.test.TestInputSimplex
import org.joml.Vector2i

class Y24D06 : Assignment<Y24D06.Input>(2024, 6) {

    override fun getRawTestInput() = TestInputSimplex(
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

    data class Input(
        val worldSize: Vector2i,
        val position: Vector2i,
        val obstacles: Set<Vector2i>
    )

    override fun getProcessedInput(raw: String): Input {
        var position = Vector2i(0, 0)
        val obstacles = mutableSetOf<Vector2i>()

        val lines = raw.lines()
        for ((y, line) in lines.withIndex())
            for ((x, c) in line.toCharArray().withIndex()) {
                if (c == '^')
                    position = Vector2i(x, y)

                if (c == '#')
                    obstacles.add(Vector2i(x, y))
            }

        val worldSize = Vector2i(lines[0].toCharArray().size, lines.size)

        return Input(worldSize, position, obstacles)
    }

    override fun solveA(input: Input): Any {
        var direction = Pair(0, -1)
        var positionX = input.position.x
        var positionY = input.position.y
        val visitedObstacles = mutableSetOf<Pair<Int, Int>>()

        val positionsVisited = mutableSetOf<Pair<Int, Int>>()
        positionsVisited.add(Pair(positionX, positionY))
        while (positionX in 0..<input.worldSize.x && positionY in 0..<input.worldSize.y) {
            val newPositionX = positionX + direction.first
            val newPositionY = positionY + direction.second

            if (newPositionX !in 0..<input.worldSize.x || newPositionY !in 0..<input.worldSize.y)
                break

            if (input.obstacles.contains(Vector2i(newPositionX, newPositionY))) {
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

    override fun solveB(input: Input): Any? = null

}

fun main() = println(Y24D06().solve())
