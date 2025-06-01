package com.nami.tools

import com.nami.round
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Exporter {
    companion object {
        fun results() {
            val results = Verifier.solved.groupBy { it.year }

            val builder = StringBuilder()
            results.forEach { (year, list) ->
                builder.appendLine("# Results $year")
                builder.appendLine("|Day|A|B|")
                builder.appendLine("|---|-|-|")
                list.forEach { result ->
                    val day = result.day
                    val a = result.task ?: ""
                    val b = result.task ?: ""
                    builder.appendLine("|$day|$a|$b|")
                }
            }

            val markdown = builder.toString()
            Files.writeString(Path.of("results.md"), markdown)
        }

        fun verification() {
            val builder = StringBuilder()
            builder.appendLine(getTableVerification("Failed", Verifier.failed))
            builder.appendLine(getTableVerification("Unsolved", Verifier.unsolved))
            builder.appendLine(getTableVerification("Solved", Verifier.solved))

            val markdown = builder.toString()
            Files.writeString(Paths.get("verification.md"), markdown)
        }

        private fun getTableVerification(title: String, content: Set<Entry>): String {
            val builder = StringBuilder()

            builder.appendLine("# $title (${content.size})")
            builder.appendLine("|Year|Day|Part|Task|Remote|Time (s)|")
            builder.appendLine("|----|---|----|----|------|--------|")
            content.sortedBy { it.id }.forEach { e ->
                val timeString = ("%.4f").format( e.timeS.toFloat()).replace(",", ".")
                builder.appendLine("|${e.year}|${e.day}|${e.part}|${e.task}|${e.remote}|${timeString}s|")
            }

            return builder.toString()
        }

    }
}

fun main() {
    Exporter.results()
    Exporter.verification()
}