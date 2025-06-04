package com.nami.task.solutions.y24

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.test.TestInputSimplex
import org.joml.Vector2i

class Y24D04 : Task<List<CharArray>>(2024, 4) {

    override fun getRawInputTest() = TestInputSimplex(
        """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String) = raw.lines().map { it.toCharArray() }.toList()

    private fun findString(
        string: String,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
        list: List<CharArray>
    ): Boolean {
        for (i in string.indices) {

            val x = position.first + i * direction.first
            val y = position.second + i * direction.second

            if (y < 0 || y >= list.size)
                return false

            if (x < 0 || x >= list[y].size)
                return false

            if (list[y][x] != string[i])
                return false
        }

        return true
    }

    private fun findStringX(string: String, position: Vector2i, list: List<CharArray>): Boolean {
        var count = 0

        for (y in -1..1)
            for (x in -1..1) {
                if (x == 0 || y == 0)
                    continue

                if (findString(string, Pair(position.x + x, position.y + y), Pair(-x, -y), list))
                    count++
            }

        return count >= 2
    }

    override fun getA() = object : SubTask<List<CharArray>> {
        override fun solve(input: List<CharArray>): Any? {
            var count = 0
            for (y in input.indices)
                for (x in input[y].indices)
                    for (yDirection in -1..1)
                        for (xDirection in -1..1) {
                            if (xDirection == 0 && yDirection == 0)
                                continue

                            if (findString("XMAS", Pair(x, y), Pair(xDirection, yDirection), input))
                                count++
                        }

            return count
        }

        override fun bonus() = 3.0
    }

    override fun getB() = object : SubTask<List<CharArray>> {
        override fun solve(input: List<CharArray>): Any? {
            var count = 0
            for (y in input.indices)
                for (x in input[y].indices)
                    if (findStringX("MAS", Vector2i(x, y), input))
                        count++

            return count
        }

        override fun bonus() = 7.0
    }

}

fun main() = Y24D04().getResult().println()
