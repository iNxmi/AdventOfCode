package com.nami

import com.nami.assignments.y15.*
import com.nami.assignments.y23.Y23D01
import com.nami.assignments.y24.*
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val assignments = listOf(
        Y15D01(),
        Y15D02(),
        Y15D03(),
        Y15D04(),
        Y15D05(),
        Y15D06(),
        Y15D07(),
        Y15D08(),

        Y23D01(),

        Y24D01(),
        Y24D02(),
        Y24D03(),
        Y24D04(),
        Y24D05(),
        Y24D06(),
        Y24D07(),
        Y24D08(),
        Y24D11(),
        Y24D15()
    )

    val solutions = assignments.map { it.solve() }

    val builder = StringBuilder()
    builder.appendLine("|Year|Day|A|B|A Test|B Test|")
    builder.appendLine("|----|---|-|-|------|------|")
    solutions.forEach { solution ->
        val year = solution.year
        val day = solution.day
        val a = solution.a
        val b = solution.b
        val bTest = solution.bTest
        builder.appendLine("|$year|$day|$a|$b|${solution.aTest ?: ""}|${bTest ?: ""}")
    }

    val markdown = builder.toString()
    Files.writeString(Path.of("solutions.md"), markdown)
}