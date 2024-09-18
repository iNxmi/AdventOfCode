package com.nami.y2023.d02

import com.nami.Assignment

class A2302: Assignment(2023, 2) {

    private fun parseData(string: String): Map<Int, List<Map<CubeColor, Int>>> {
        val data = mutableMapOf<Int, MutableList<MutableMap<CubeColor, Int>>>()

        val regexId = Regex("(?<=Game\\s)\\d+")
        var rollsRegex = Regex("(?<=:)\\s*(.*)")

        val lines = string.lines()
        lines.forEach { line ->
            val id = regexId.find(line, 0)!!.value.toInt()
            val rolls = rollsRegex.find(line, 0)!!.value.split(";")

            var roll1 = rolls[0]

        }

        return data
    }

    override fun solve(input: String): Int {
        parseData(input)
        return 0
    }

    enum class CubeColor {
        RED, GREEN, BLUE
    }

}

fun main() {
    println(A2302().solve())
}