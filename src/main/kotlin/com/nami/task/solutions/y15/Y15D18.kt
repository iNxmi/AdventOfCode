package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.test.TestInputDuplex
import org.joml.Vector2i

class Y15D18 : Task<Y15D18.World>(2015, 18) {

    override fun getRawInputTest() = TestInputDuplex(
        """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..    
        """.trimIndent(),
        """
            ##.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####.#
        """.trimIndent()
    )

    data class World(
        val size: Vector2i,
        val cells: Set<Vector2i>
    ) {

    }

    override fun getProcessedInput(raw: String): World {
        val width = raw.lines()[0].length
        val height = raw.lines().size
        val size = Vector2i(width, height)

        val cells = mutableSetOf<Vector2i>()
        raw.lines().withIndex().forEach { (y, line) ->
            line.toCharArray().withIndex().forEach { (x, char) ->
                if (char != '#')
                    return@forEach

                val position = Vector2i(x, y)
                cells.add(position)
            }
        }

        return World(size, cells)
    }

    private fun getNeighborCount(cells: Set<Vector2i>, position: Vector2i): Int {
        var count = 0
        for (y in -1..1)
            for (x in -1..1) {
                if (x == 0 && y == 0)
                    continue

                val offset = Vector2i(position).add(x, y)
                if (cells.contains(offset))
                    count++
            }

        return count
    }

    private fun update(world: World, cells: Set<Vector2i>): Set<Vector2i> {
        val updated = mutableSetOf<Vector2i>()
        for (y in 0..<world.size.y)
            for (x in 0..<world.size.x) {
                val position = Vector2i(x, y)
                val count = getNeighborCount(cells, position)
                val on = cells.contains(position)

                if (on && count in 2..3)
                    updated.add(position)
                else if (!on && count == 3)
                    updated.add(position)
            }

        return updated
    }

    private fun simulateA(world: World, iterations: Int): Int {
        val cells = world.cells.toMutableSet()
        for (i in 0..<iterations) {
            val updated = update(world, cells)
            cells.clear()
            cells.addAll(updated)
        }

        return cells.size
    }

    private fun simulateB(world: World, iterations: Int): Int {
        val cells = world.cells.toMutableSet()
        for (i in 0..<iterations) {
            val updated = update(world, cells).toMutableSet()
            updated.add(Vector2i(0, 0))
            updated.add(Vector2i(0, world.size.y - 1))
            updated.add(Vector2i(world.size.x - 1, 0))
            updated.add(Vector2i(world.size.x - 1, world.size.y - 1))

            cells.clear()
            cells.addAll(updated)
        }

        return cells.size
    }

    override fun getA() = object : SubTask<World> {
        override fun solve(input: World) = simulateA(input, 100)
        override fun test(input: World) = simulateA(input, 4)
    }

    override fun getB() = object : SubTask<World> {
        override fun solve(input: World) = simulateB(input, 100)
        override fun test(input: World) = simulateB(input, 5)
    }

}

fun main() = Y15D18().getResult().println()