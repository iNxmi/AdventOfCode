package com.nami.aoc.task.solution.y23

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2023D02 : Task<Map<Int, List<Y2023D02.Draw>>>(2023, 2) {

    data class Draw(
        val red: Int,
        val green: Int,
        val blue: Int
    )

    override fun getProcessedInput(raw: String): Map<Int, List<Draw>> {
        val string = raw
            .replace(("Game \\d*:").toRegex(), "")
            .replace("red", "r")
            .replace("green", "g")
            .replace("blue", "b")
            .replace(" ", "")

        val result = mutableMapOf<Int, List<Draw>>()
        val lines = string.lines()
        lines.withIndex().forEach { (index, line) ->
            val list = mutableListOf<Draw>()

            val split = line.split(";")
            split.forEach { drawRaw ->
                val drawSplit = drawRaw.split(",")

                var red = 0
                var green = 0
                var blue = 0

                drawSplit.forEach {
                    val color = it.last()
                    val count = it.substring(0..<it.length - 1).toInt()

                    if (color == 'r')
                        red = count
                    if (color == 'g')
                        green = count
                    if (color == 'b')
                        blue = count
                }

                list.add(Draw(red, green, blue))
            }

            result[index] = list
        }

        return result
    }

    override fun getPartA() = object : Part<Map<Int, List<Draw>>>(
        year, day, Type.A,
        bonus = 2.0
    ) {
        override fun solve(input: Map<Int, List<Draw>>): Any {
            val filtered = input.filter { (_, game) ->
                game.maxOf { it.red } <= 12 &&
                        game.maxOf { it.green } <= 13 &&
                        game.maxOf { it.blue } <= 14
            }

            //because the list starts with index 0 but the puzzle input starts with index 1
            return filtered.keys.sumOf { it + 1 }
        }
    }

    override fun getPartB() = object : Part<Map<Int, List<Draw>>>(
        year, day, Type.B,
        bonus = 3.0
    ) {
        override fun solve(input: Map<Int, List<Draw>>): Any {
            val minimal = input.map { (_, game) ->
                val red = game.maxOf { it.red }
                val green = game.maxOf { it.green }
                val blue = game.maxOf { it.blue }
                Draw(red, green, blue)
            }

            return minimal.sumOf { it.red * it.green * it.blue }
        }
    }

}