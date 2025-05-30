package com.nami

import com.nami.assignments.y15.*
import com.nami.assignments.y23.Y23D01
import com.nami.assignments.y24.*
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.round

fun export() {
    val assignments = listOf(
        Y15D01(),
        Y15D02(),
        Y15D03(),
        Y15D04(),
        Y15D05(),
        Y15D06(),
        Y15D07(),
        Y15D08(),
        Y15D09(),
        Y15D10(),
        Y15D11(),
        Y15D12(),
        Y15D14(),

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

    val builder = StringBuilder()
    builder.appendLine("|Year|Day|A|B|")
    builder.appendLine("|----|---|-|-|")
    assignments.withIndex().forEach { (index, assignment) ->
        println("${assignment.year}_${assignment.day} - (${index + 1}/${assignments.size}) - ${round(index.toDouble() / assignments.size.toDouble() * 10000.0) / 100.0}%")

        val solution = assignment.solve()

        val year = solution.year
        val day = solution.day
        val a = if (solution.a == -1) "" else solution.a
        val b = if (solution.b == -1) "" else solution.b
        builder.appendLine("|$year|$day|$a|$b|")
    }

    val markdown = builder.toString()
    Files.writeString(Path.of("solutions.md"), markdown)
}

fun main() = export()