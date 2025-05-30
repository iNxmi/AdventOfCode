package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y15D08 : Assignment<List<String>, Int>(2015, 8) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex(
            """
            ""
            "abc"
            "aaa\"aaa"
            "\x27"
            """.trimIndent()
        )
    }

    override fun getProcessedInput(raw: String): List<String> = raw.lines()

    @OptIn(ExperimentalStdlibApi::class)
    private fun escape(str: String): String {
        var result = str
            .subSequence(1..<str.length - 1).toString()
            .replace("\\\\", "\\")
            .replace("\\\"", "\"")

        val regex = ("\\\\x([a-f]|\\d){2}").toRegex()
        val finds = regex.findAll(result)

        finds.forEach { a ->
            result = result.replace(a.value, a.value.substring(2..3).hexToInt().toChar().toString())
        }

        return result
    }

    override fun solveA(input: List<String>): Int {
        val code = input.sumOf { it.length }
        val memory = input.map { escape(it) }.sumOf { it.length }
        return code - memory
    }

    private fun unescape(str: String): String {
        var result = str
            .replace("\\","\\\\")
            .replace("\"","\\\"")

        result = "\"$result\""

        return result
    }

    override fun solveB(input: List<String>): Int {
        val newlyEncoded = input.map { unescape(it) }.sumOf { it.length }
        val original = input.sumOf { it.length }
        return newlyEncoded - original
    }

}

fun main() = println(Y15D08().solve())
