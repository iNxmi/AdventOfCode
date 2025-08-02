package com.nami.aoc.task.solution.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import org.joml.Vector2i

class Y2024D08 : Task<Y2024D08.Input>(2024, 8) {

    data class Input(
        val size: Vector2i,
        val nodes: Map<Char, Set<Vector2i>>
    )

    override fun getProcessedInput(raw: String): Input {
        var width = 0

        val map = mutableMapOf<Char, MutableSet<Vector2i>>()
        val height = raw.lines().size
        for ((y, line) in raw.lines().withIndex()) {
            width = line.length
            for ((x, char) in line.toCharArray().withIndex()) {
                if (char == '.')
                    continue

                if (!map.containsKey(char))
                    map[char] = mutableSetOf()

                map[char]!!.add(Vector2i(x, y))
            }
        }

        val size = Vector2i(width, height)
        return Input(size, map)
    }

    private fun getAntinodes(positionA: Vector2i, positionB: Vector2i, worldSize: Vector2i): Set<Vector2i> {
        val direction = Vector2i(positionB).sub(positionA)

        val antinodes = mutableSetOf<Vector2i>()

        var i = 0
        while (true) {
            val antinode = Vector2i(positionA).sub(Vector2i(direction).mul(i))

            if (antinode.x < 0 || antinode.x >= worldSize.x)
                break
            if (antinode.y < 0 || antinode.y >= worldSize.y)
                break

            antinodes.add(antinode)
            i++
        }

        i = 0
        while (true) {
            val antinode = Vector2i(positionA).add(Vector2i(direction).mul(i))

            if (antinode.x < 0 || antinode.x >= worldSize.x)
                break
            if (antinode.y < 0 || antinode.y >= worldSize.y)
                break

            antinodes.add(antinode)
            i++
        }

        antinodes.remove(positionA)
        antinodes.remove(positionB)

        return antinodes
    }

    override fun getPartA() = object : Part<Input>(
        year, day, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: Input): Any {
            val antinodes = mutableSetOf<Vector2i>()
            for (key in input.nodes.keys) {
                for (p1 in input.nodes[key]!!)
                    for (p2 in input.nodes[key]!!) {
                        if (p1 == p2)
                            continue

                        val direction = Vector2i(p2).sub(p1)

                        val antinode1 = Vector2i(p2).add(direction)
                        if (antinode1.x in 0..<input.size.x && antinode1.y in 0..<input.size.y)
                            antinodes.add(antinode1)

                        val antinode2 = Vector2i(p1).sub(direction)
                        if (antinode2.x in 0..<input.size.x && antinode2.y in 0..<input.size.y)
                            antinodes.add(antinode2)
                    }
            }

            return antinodes.size
        }
    }

    override fun getPartB() = object : Part<Input>(
        year, day, Type.B
    ) {
        override fun solve(input: Input): Any? {
            val antinodes = mutableSetOf<Vector2i>()

            for (key in input.nodes.keys)
                for (p1 in input.nodes[key]!!)
                    for (p2 in input.nodes[key]!!)
                        if (p1 != p2)
                            antinodes.addAll(getAntinodes(p1, p2, input.size))

            return antinodes.size
        }
    }

}
