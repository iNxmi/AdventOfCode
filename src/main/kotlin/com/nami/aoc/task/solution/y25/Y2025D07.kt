package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.y25.Y2025D06.Problem
import org.joml.Vector2i
import java.lang.Math.pow
import kotlin.math.pow

class Y2025D07 : Task<Y2025D07.Diagram>(2025, 7) {

    data class Diagram(
        val start: Vector2i,
        val height: Int,
        val splitters: Set<Vector2i>
    )

    override fun getProcessedInput(raw: String): Diagram {
        var start = Vector2i()
        val splitters = mutableSetOf<Vector2i>()

        val lines = raw.lines()
        for ((y, line) in lines.withIndex())
            for ((x, char) in line.withIndex()) {
                var position = Vector2i(x, y)

                when (char) {
                    'S' -> start = position
                    '^' -> splitters.add(position)
                }
            }

        return Diagram(start, lines.size, splitters)
    }

    private fun step(y: Int, positions: Set<Vector2i>, splitters: Set<Vector2i>): Set<Vector2i> {
        val currentPositions = positions.filter { it.y == y }

        val result = positions.toMutableSet()
        for (position in currentPositions) {
            if (!splitters.contains(Vector2i(position).add(0, 1))) {
                result.add(Vector2i(position).add(0, 1))
            } else {
                result.add(Vector2i(position).add(-1, 1))
                result.add(Vector2i(position).add(1, 1))
            }
        }

        return result.toSet()
    }

    fun getNumberSplits(diagram: Diagram): Int {
        var positions = setOf(diagram.start)
        for (y in 0 until diagram.height)
            positions = step(y, positions, diagram.splitters)

        val count = diagram.splitters.count {
            val above = Vector2i(it).add(0, -1)
            positions.contains(above)
        }

        return count
    }

    override fun getPartA() = object : Part.A<Diagram>(this) {
        override fun solve(input: Diagram) = getNumberSplits(input)
    }

    override fun getPartB() = object : Part.B<Diagram>(this) {
        override fun solve(input: Diagram) = (2.0).pow(getNumberSplits(input).toDouble()).toLong()
    }

}

fun main() = Y2025D07().getTestVerifications().print()
//fun main() = Y2025D07().getResults().print()
//fun main() = Y2025D07().getVerifications().print()