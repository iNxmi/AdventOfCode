package com.nami.assignments.y24

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y24D04 : Assignment<List<CharArray>>(2024, 4) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
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
    }

    override fun getProcessedInput(raw: String): List<CharArray> {
        return raw.lines().map { it.toCharArray() }.toList()
    }

    fun findString(
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

    fun findStringX(string: String, position: Pair<Int, Int>, list: List<CharArray>): Boolean {
        var count = 0

        for (y in -1..1)
            for (x in -1..1) {
                if (x == 0 || y == 0)
                    continue

                if (findString(string, Pair(position.first + x, position.second + y), Pair(-x, -y), list))
                    count++
            }

        return count >= 2
    }

    override fun solveA(input: List<CharArray>): Int {
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

    override fun solveB(input: List<CharArray>): Int {
        var count = 0
        for (y in input.indices)
            for (x in input[y].indices)
                if (findStringX("MAS", Pair(x, y), input)) count++

        return count
    }

}