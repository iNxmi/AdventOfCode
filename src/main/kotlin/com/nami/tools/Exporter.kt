package com.nami.tools

import com.nami.task.Status
import com.nami.task.Task
import com.nami.task.Verification
import net.steppschuh.markdowngenerator.table.Table
import java.nio.file.Files
import java.nio.file.Paths

class Exporter {

    val verifications = Task.getAll().map { it.getVerifications() }.flatMap { setOf(it.first, it.second) }

    val failed = verifications.filter { it.status == Status.FAILED }.toSet()
    val unsolved = verifications.filter { it.status == Status.UNSOLVED }.toSet()
    val solved = verifications.filter { it.status == Status.SOLVED }.toSet()

    fun export() {
        val builder = StringBuilder()
        builder.appendLine("# Failed")
        builder.appendLine(getTableVerificationHTML(failed))
        builder.appendLine("# Unsolved")
        builder.appendLine(getTableVerificationHTML(unsolved))
        builder.appendLine("# Solved")
        builder.appendLine(getTableVerificationHTML(solved))

        val markdown = builder.toString()
        Files.writeString(Paths.get("summary.md"), markdown)
    }

    private fun getTableVerificationHTML(content: Set<Verification>): String {
        val table = Table.Builder().apply {
            withAlignment(Table.ALIGN_RIGHT)
            addRow("Year", "Day", "Part", "Expected", "Actual", "Time (s)", "Bonus (€)")

            content.forEach {
                addRow(
                    it.uid.year,
                    it.uid.day,
                    it.uid.part,
                    it.expected,
                    it.result.value,
                    it.result.timeInSeconds,
                    "0.00€"
                )
            }
        }

        return table.build().toString()
    }
}

fun main() {
    Exporter().export()
}
