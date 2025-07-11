package com.nami.aoc.task.solutions.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex

class Y16D07 : Task<List<String>>(2016, 7) {

    data class IPv7(val sequences: List<String>, val hypernet: List<String>)

    override fun getRawInputTest() = InputSimplex(
        """
        abba[mnop]qrst
        abcd[bddb]xyyx
        aaaa[qwer]tyui
        ioxxoj[asdfgh]zxcvbn
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>> {
        override fun solve(input: List<String>) = null
    }

    override fun getPartB() = object : Part<List<String>> {
        override fun solve(input: List<String>) = null
    }

}

fun main() = Y16D07().printResult()
//fun main() = Y16D07().printVerification()
