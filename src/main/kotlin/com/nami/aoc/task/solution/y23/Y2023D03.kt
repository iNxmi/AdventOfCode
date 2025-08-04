package com.nami.aoc.task.solution.y23

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.y23.Y2023D03.Input
import org.joml.Vector2i


class Y2023D03 : Task<Input>(2023, 3) {

    data class Input(val numbers: Map<MultiPosition, Int>, val points: Set<Vector2i>)

    data class MultiPosition(val x: IntRange, val y: Int) {
        fun getAllPositions() = x.map { x -> Vector2i(x, y) }

        private fun getAllDirections(position: Vector2i): Set<Vector2i> {
            val set = mutableSetOf<Vector2i>()
            for (y in -1..1)
                for (x in -1..1)
                    set.add(Vector2i(position).add(x, y))
            return set
        }

        fun getAllDirections() = getAllPositions().flatMap { getAllDirections(it) }.toSet()
    }

    override fun getProcessedInput(raw: String): Input {
        val numbers = mutableMapOf<MultiPosition, Int>()
        val points = mutableSetOf<Vector2i>()
        raw.lines().withIndex().forEach { (y, line) ->
            numbers.putAll(
                Regex("[0-9]+")
                    .findAll(line)
                    .associate {
                        val position = MultiPosition(it.range, y)
                        val value = it.value.toInt()
                        Pair(position, value)
                    }
            )

            points.addAll(
                Regex("[^0-9.\n]")
                    .findAll(line)
                    .map { Vector2i(it.range.first, y) }
                    .toSet()
            )
        }
        return Input(numbers, points)
    }

    override fun getPartA() = object : Part<Input>(
        this, Suffix.A,
        bonus = 2.0
    ) {
        override fun solve(input: Input) = input.numbers.filter { (position, _) ->
            position.getAllDirections().any { input.points.contains(it) }
        }.map { (_, value) -> value }.sum()
    }

    override fun getPartB() = object : Part<Input>(
        this, Suffix.B,
        bonus = 3.0
    ) {
        override fun solve(input: Input): Any {
            val map = mutableMapOf<Vector2i, MutableList<Int>>()
            input.numbers.forEach { (position, value) ->
                position.getAllDirections().forEach { p ->
                    if (input.points.contains(p)) {
                        if (!map.containsKey(p))
                            map[p] = mutableListOf()

                        map[p]!!.add(value)
                    }
                }
            }

            return map.filter { (_, list) -> list.size == 2 }
                .map { (_, list) -> list[0] * list[1] }
                .sum()
        }
    }

}