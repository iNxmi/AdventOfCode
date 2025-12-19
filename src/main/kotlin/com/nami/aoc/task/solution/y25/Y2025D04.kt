package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import org.joml.Vector2i

class Y2025D04 : Task<Set<Vector2i>>(2025, 4) {

    companion object {
        const val MAX_ADJACENT_ROLLS = 4
    }

    override fun getProcessedInput(raw: String): Set<Vector2i> {
        val result = mutableSetOf<Vector2i>()

        val lines = raw.lines()
        for ((y, line) in lines.withIndex()) {
            val chars = line.toCharArray()
            for ((x, char) in chars.withIndex())
                if (char == '@')
                    result.add(Vector2i(x, y))
        }

        return  result
    }

    private val offsets = getOffsets()
    private fun getOffsets(): Set<Vector2i> {
        val result = mutableSetOf<Vector2i>()

        for (y in -1..1)
            for (x in -1..1) {
                if (x == 0 && y == 0)
                    continue

                result.add(Vector2i(x, y))
            }

        return result
    }

    private fun getRemovable(grid: Set<Vector2i>) = grid.filter { position ->
        var adjacent = 0
        for (offset in offsets) {
            val offsetPosition = Vector2i(position).add(offset)
            if (grid.contains(offsetPosition))
                adjacent++
        }

        adjacent < MAX_ADJACENT_ROLLS
    }.toSet()

    override fun getPartA() = object : Part.A<Set<Vector2i>>(task = this, bonus = 5.0) {
        override fun solve(input: Set<Vector2i>) = getRemovable(input).size
    }

    override fun getPartB() = object : Part.B<Set<Vector2i>>(task = this, bonus = 10.0) {
        override fun solve(input: Set<Vector2i>): Int {
            val grid = input.toMutableSet()

            while (true) {
                val previous = grid.toSet()

                val removable = getRemovable(grid)
                grid.removeAll(removable)

                if (previous == grid)
                    break
            }

            return input.size - grid.size
        }
    }

}

//fun main() = Y2025D04().getTestVerifications().print()
//fun main() = Y2025D04().getResults().print()
fun main() = Y2025D04().getVerifications().print()