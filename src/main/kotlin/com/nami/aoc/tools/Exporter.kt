package com.nami.aoc.tools

import com.nami.aoc.task.Status
import com.nami.aoc.task.Task
import com.nami.aoc.task.Verification
import net.steppschuh.markdowngenerator.table.Table
import java.nio.file.Files
import java.nio.file.Paths

class Exporter {

    val verifications = Task.Companion.getAll().map { it.getVerifications() }.flatMap { setOf(it.first, it.second) }

    val failed = verifications.filter { it.status == Status.FAILED }.toSet()
    val unsolved = verifications.filter { it.status == Status.UNSOLVED }.toSet()
    val solved = verifications.filter { it.status == Status.SOLVED }.toSet()

    fun export() {
        val builder = StringBuilder()
        builder.appendLine("# Failed (${failed.size})")
        builder.appendLine(getTableVerificationHTML(failed))
        builder.appendLine("# Unsolved  (${unsolved.size})")
        builder.appendLine(getTableVerificationHTML(unsolved))
        builder.appendLine("# Solved  (${solved.size})")
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
                    ("%.2fs").format(it.result.timeInSeconds),
                    if (it.part.bonus() != null) ("%.2f€").format(it.part.bonus()) else ""
                )
            }
        }

        return table.build().toString()
    }

}

fun main() {
    Exporter().export()
}
