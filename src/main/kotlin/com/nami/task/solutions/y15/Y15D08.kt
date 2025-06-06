package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.input.InputSimplex

class Y15D08 : Task<List<String>>(2015, 8) {

    override fun getRawInputTest() = InputSimplex(
        """
        ""
        "abc"
        "aaa\"aaa"
        "\x27"
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String): List<String> = raw.lines()

    @OptIn(ExperimentalStdlibApi::class)
    private fun escape(string: String): String {
        var result = string
            .subSequence(1..<string.length - 1).toString()
            .replace("\\\\", "\\")
            .replace("\\\"", "\"")

        val regex = ("\\\\x([a-f]|\\d){2}").toRegex()
        val finds = regex.findAll(result)

        finds.forEach { a ->
            result = result.replace(a.value, a.value.substring(2..3).hexToInt().toChar().toString())
        }

        return result
    }

    private fun unescape(string: String): String {
        var result = string
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")

        result = "\"$result\""

        return result
    }

    override fun getSubTaskA() = object : SubTask<List<String>> {
        override fun solve(input: List<String>): Any {
            val code = input.sumOf { it.length }
            val memory = input.map { escape(it) }.sumOf { it.length }
            return code - memory
        }
        override fun bonus() = 5.0
    }

    override fun getSubTaskB() = object : SubTask<List<String>> {
        override fun solve(input: List<String>): Any {
            val newlyEncoded = input.map { unescape(it) }.sumOf { it.length }
            val original = input.sumOf { it.length }
            return newlyEncoded - original
        }
        override fun bonus() = 10.0
    }

}

fun main() = Y15D08().getResult().println()
