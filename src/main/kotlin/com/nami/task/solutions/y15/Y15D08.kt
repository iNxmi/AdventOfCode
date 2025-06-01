package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.Task
import com.nami.task.test.TestInputSimplex

class Y15D08 : Task<List<String>>(2015, 8) {

    override fun getRawTestInput() = TestInputSimplex(
        """
        ""
        "abc"
        "aaa\"aaa"
        "\x27"
        """.trimIndent()
    )

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

    override fun solveA(input: List<String>): Any {
        val code = input.sumOf { it.length }
        val memory = input.map { escape(it) }.sumOf { it.length }
        return code - memory
    }

    private fun unescape(str: String): String {
        var result = str
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")

        result = "\"$result\""

        return result
    }

    override fun solveB(input: List<String>): Any {
        val newlyEncoded = input.map { unescape(it) }.sumOf { it.length }
        val original = input.sumOf { it.length }
        return newlyEncoded - original
    }

}

fun main() = Y15D08().solve().println()
