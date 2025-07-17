package com.nami.aoc.tools

import com.nami.aoc.task.Status
import com.nami.aoc.task.Task
import com.nami.aoc.task.Verification
import net.steppschuh.markdowngenerator.link.Link
import net.steppschuh.markdowngenerator.table.Table
import java.nio.file.Files
import java.nio.file.Paths

class Exporter {

    fun export() {
        val timeStart = System.currentTimeMillis()
        val tasks = Task.getAll()
        val verifications = tasks.withIndex().map { indexed ->
            val task = indexed.value
            println("${indexed.index + 1}/${tasks.size} (${task.uid.year}_${task.uid.day})")

            val verifications = task.getVerifications()
            task.printVerifications(verifications)

            verifications
        }.flatMap { setOf(it.first, it.second) }

        val failed = verifications.filter { it.status == Status.FAILED }.toSet()
        val unsolved = verifications.filter { it.status == Status.UNSOLVED }.toSet()
        val solved = verifications.filter { it.status == Status.SOLVED }.toSet()

        val builder = StringBuilder()
        builder.appendLine("# Failed (${failed.size} / ${verifications.size})")
        builder.appendLine(getTableVerificationHTML(failed))
        builder.appendLine("# Unsolved  (${unsolved.size} / ${verifications.size})")
        builder.appendLine(getTableVerificationHTML(unsolved))
        builder.appendLine("# Solved  (${solved.size} / ${verifications.size})")
        builder.appendLine(getTableVerificationHTML(solved))

        val timeEnd = System.currentTimeMillis()
        builder.appendLine("# Total Export Time (s): ${(timeEnd - timeStart) / 1000.0}s")

        val markdown = builder.toString()
        Files.writeString(Paths.get("summary.md"), markdown)
    }

    private fun getTableVerificationHTML(content: Set<Verification>): String {
        val table = Table.Builder().apply {
            withAlignments(
                Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT,
                Table.ALIGN_LEFT,
                Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT,
                Table.ALIGN_LEFT
            )
            addRow("Year", "Day", "Part", "Expected", "Actual", "Time (s)", "Bonus (€)", "URL")

            content.forEach {
                addRow(
                    it.uid.year,
                    it.uid.day,
                    it.uid.part,
                    it.expected,
                    it.result.value,
                    ("%.2fs").format(it.result.timeInSeconds),
                    if (it.part.bonus() != null) ("%.2f€").format(it.part.bonus()) else "",
                    Link(it.task.url)
                )
            }
        }

        return table.build().toString()
    }

}

fun main() = Exporter().export()
