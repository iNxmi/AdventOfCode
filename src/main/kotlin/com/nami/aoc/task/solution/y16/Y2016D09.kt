package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2016D09 : Task<String>(2016, 9) {

    override fun getProcessedInput(raw: String) = raw.replace(Regex("\\s"), "")

    override fun getPartA() = object : Part<String>(
        this, Type.A
    ) {
        override fun solve(input: String): Any {
            var result = input
            var cursor = 0
            while (true) {
                val match = Regex("\\((\\d+)x(\\d+)\\)").find(result, cursor) ?: return result.length
                val length = match.groupValues[1].toInt()
                val repetition = match.groupValues[2].toInt()

                result = result.replaceRange(
                    match.range.start,
                    match.range.endInclusive + length + 1,
                    result.substring(match.range.endInclusive + 1, match.range.endInclusive + 1 + length)
                        .repeat(repetition)
                )
                cursor = match.range.start + length * repetition
            }
        }
    }

    /*
   override fun solve(input: List<String>): Any {
            val sbRoot = StringBuilder()
            input.forEach { line ->
                var result = line
                println(result)
                while (true) {
                    val match = Regex("(\\(\\d+x\\d+\\))((\\(\\d+x\\d+\\))*\\w+)").find(result) ?: break
                    println(match.groupValues)

                    val operation = match.groupValues[1]
                    val length = Regex("\\d+").findAll(operation).toList()[0].value.toInt()
                    val repetition = Regex("\\d+").findAll(operation).toList()[1].value.toInt()

                    result =
                        result.replaceRange(
                            match.range.start,
                            operation.length + length + match.range.start,
                            match.groupValues[2].substring(0, length).repeat(repetition).replace("(", "[")
                                .replace(")", "]")
                        )
                }
                sbRoot.append(result)
                sbRoot.appendLine()
            }
     */

    override fun getPartB() = object : Part<String>(
        this, Type.B
    ) {
        override fun solve(input: String) = null
    }

}
