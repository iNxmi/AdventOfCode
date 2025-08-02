package com.nami.aoc.task.solution.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.common.Direction
import org.joml.Vector2i
import javax.naming.directory.InvalidAttributesException

class Y2024D15 : Task<Y2024D15.Input>(2024, 15) {

    data class Input(val world: World, val instructions: List<Direction>)

    data class World(
        val size: Vector2i = Vector2i(),
        val static: MutableSet<Vector2i> = mutableSetOf(),
        val rigid: MutableSet<Vector2i> = mutableSetOf(),
        val player: Vector2i = Vector2i()
    ) {

        fun getPossibleMoveCount(position: Vector2i, direction: Direction): Int {
            var count = 1
            while (!static.contains(Vector2i(position).add(Vector2i(direction.vector).mul(count))))
                count++

            return count - 1
        }

    }

    override fun getProcessedInput(raw: String): Input {
        val world = World()
        val rawWorld = raw.split("\n\n")[0].trim()

        val width = rawWorld.lines()[0].length
        val height = rawWorld.lines().size
        world.size.set(width, height)

        for ((y, line) in rawWorld.lines().withIndex())
            for ((x, char) in line.toCharArray().withIndex()) {
                val position = Vector2i(x, y)

                if (char == '#')
                    world.static.add(position)

                if (char == 'O')
                    world.rigid.add(position)

                if (char == '@')
                    world.player.set(position)
            }

        val instructions = mutableListOf<Direction>()
        val rawInstructions = raw.split("\n\n")[1].trim().replace("\n", "").trim()
        for (c in rawInstructions.toCharArray()) {
            val direction = when (c) {
                '^' -> Direction.NORTH
                '>' -> Direction.EAST
                'v' -> Direction.SOUTH
                '<' -> Direction.WEST
                else -> throw InvalidAttributesException(c.toString())
            }
            instructions.add(direction)
        }

        return Input(world, instructions)
    }

    override fun getPartA() = object : Part<Input>(
        year, day, Type.A
    ) {
        override fun solve(input: Input) = null
    }

    override fun getPartB() = object : Part<Input>(
        year, day, Type.B
    ) {
        override fun solve(input: Input) = null
    }

}