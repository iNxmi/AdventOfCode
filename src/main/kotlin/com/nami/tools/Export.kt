package com.nami.tools

import com.nami.task.Task
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.round

fun export() {
    val tasks = Task.getAll().groupBy { it.year }

    val builder = StringBuilder()
    tasks.forEach { (year, list) ->
        builder.appendLine("# Results $year")
        builder.appendLine("|Day|A|B|")
        builder.appendLine("|---|-|-|")
        list.withIndex().forEach { (index, task) ->
            println("${task.id} - (${index + 1}/${list.size}) - ${round((index + 1).toDouble() / list.size.toDouble() * 10000.0) / 100.0}%")

            val result = task.solve()

            val day = result.day
            val a = result.a ?: ""
            val b = result.b ?: ""
            builder.appendLine("|$day|$a|$b|")
        }
    }

    val markdown = builder.toString()
    Files.writeString(Path.of("results.md"), markdown)
}

fun main() = export()