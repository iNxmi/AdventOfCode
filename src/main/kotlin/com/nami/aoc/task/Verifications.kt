package com.nami.aoc.task

import net.steppschuh.markdowngenerator.table.Table

data class Verifications(
    val uid: UID,
    val a: Verification,
    val b: Verification
) {

    override fun toString() = Table.Builder()
        .withAlignments(
            Table.ALIGN_LEFT,
            Table.ALIGN_LEFT,
            Table.ALIGN_RIGHT,
            Table.ALIGN_RIGHT,
            Table.ALIGN_RIGHT
        )
        .addRow("${uid.year}/${uid.day}", "Status", "Expected", "Actual", "Time (s)")
        .addRow("A", a.status, a.expected, a.result.value, ("%.2fs").format(a.result.timeInSeconds))
        .addRow("B", b.status, b.expected, b.result.value, ("%.2fs").format(b.result.timeInSeconds))
        .build()
        .toString()

}