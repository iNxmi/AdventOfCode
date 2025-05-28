package com.nami.assignments.y24

import com.nami.Assignment
import com.nami.misc.Direction
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import org.joml.Vector2i
import javax.naming.directory.InvalidAttributesException

class Y24D15 : Assignment<Y24D15.ProcessedInput>(2024, 15) {

    data class ProcessedInput(val world: World, val instructions: List<Direction>)

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

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########

            <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
            vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
            ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
            <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
            ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
            ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
            >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
            <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
            ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
            v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
        """.trimIndent()
        )
    }

    override fun getProcessedInput(raw: String): ProcessedInput {
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
                else -> throw InvalidAttributesException()
            }
            instructions.add(direction)
        }

        return ProcessedInput(world, instructions)
    }

    override fun solveA(input: ProcessedInput): Long {


        return -1
    }

    override fun solveB(input: ProcessedInput): Long {
        return -1
    }

}