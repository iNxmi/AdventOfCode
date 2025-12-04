package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import org.joml.Vector2i

class Y2025D04 : Task<Y2025D04.Grid>(2025, 4) {

    data class Grid(
        val width: Int,
        val height: Int,
        val grid: Set<Vector2i>
    )

    override fun getProcessedInput(raw: String): Grid {
        val result = mutableSetOf<Vector2i>()

        val lines = raw.lines()
        for ((y, line) in lines.withIndex()) {
            val chars = line.toCharArray()
            for ((x, char) in chars.withIndex())
                if (char == '@')
                    result.add(Vector2i(x, y))
        }

        val width = lines[0].length
        val height = lines.size

        return Grid(width, height, result)
    }

    fun remove(roles: Set<Vector2i>) = roles.filter { position ->
        var count = 0
        for (yOffset in -1..1)
            for (xOffset in -1..1) {
                if (xOffset == 0 && yOffset == 0)
                    continue

                val offset = Vector2i(xOffset, yOffset)
                val offsetPosition = Vector2i(position).add(offset)
                if (roles.contains(offsetPosition))
                    count++
            }

        count >= 4
    }.toSet()

    fun getRemovable(roles: Set<Vector2i>) = roles.filter { position ->
        var count = 0
        for (yOffset in -1..1)
            for (xOffset in -1..1) {
                if (xOffset == 0 && yOffset == 0)
                    continue

                val offset = Vector2i(xOffset, yOffset)
                val offsetPosition = Vector2i(position).add(offset)
                if (roles.contains(offsetPosition))
                    count++
            }

        count < 4
    }.toSet()

    override fun getPartA() = object : Part.A<Grid>(this) {
        override fun solve(input: Grid) = input.grid.size - remove(input.grid).size
    }

    override fun getPartB() = object : Part.B<Grid>(this) {
        override fun solve(input: Grid) : Int {
            val grid = input.grid.toMutableSet()

            while(true) {
                val previous = grid.toSet()

                grid.removeAll(getRemovable(grid))

                if(previous == grid)
                    break
            }

            return input.grid.size - grid.size
        }
    }

}

//fun main() = Y2025D04().getTestVerifications().print()
fun main() = Y2025D04().getResults().print()
//fun main() = Y2025D04().getVerifications().print()