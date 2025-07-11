package com.nami.aoc.task.solutions.y23

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex

class Y23D02 : Task<Map<Int, List<Y23D02.Draw>>>(2023, 2) {

    override fun getRawInputTest() = InputSimplex(
        """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()
    )

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

    override fun getPartA() = object : Part<Map<Int, List<Draw>>> {
        override fun solve(input: Map<Int, List<Draw>>): Any {
            val filtered = input.filter { (_, game) ->
                game.maxOf { it.red } <= 12 &&
                        game.maxOf { it.green } <= 13 &&
                        game.maxOf { it.blue } <= 14
            }

            //because the list starts with index 0 but the puzzle input starts with index 1
            return filtered.keys.sumOf { it + 1 }
        }

        override fun bonus() = 2.0
    }

    override fun getPartB() = object : Part<Map<Int, List<Draw>>> {
        override fun solve(input: Map<Int, List<Draw>>): Any {
            val minimal = input.map { (_, game) ->
                val red = game.maxOf { it.red }
                val green = game.maxOf { it.green }
                val blue = game.maxOf { it.blue }
                Draw(red, green, blue)
            }

            return minimal.sumOf { it.red * it.green * it.blue }
        }

        override fun bonus() = 3.0
    }

}

fun main() = Y23D02().printResult()
