package com.nami.aoc.task

import net.steppschuh.markdowngenerator.link.Link
import net.steppschuh.markdowngenerator.table.Table

data class Results(
    val task: Task<*>,
    val a: Result,
    val b: Result,
    val aTest: Result?,
    val bTest: Result?
) {

    override fun toString(): String {
        val builder = Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT)
            .addRow("${task.uid.year}/${task.uid.day}", "Result", "Time (s)", "URL")
            .addRow("A", a.value, ("%.2fs").format(a.timeInSeconds))
            .addRow("B", b.value, ("%.2fs").format(b.timeInSeconds))

        if (aTest != null)
            builder.addRow("A_TEST", aTest.value, ("%.2fs").format(aTest.timeInSeconds))

        if (bTest != null)
            builder.addRow("B_TEST", bTest.value, ("%.2fs").format(bTest.timeInSeconds))

        return builder.build().toString()
    }

}