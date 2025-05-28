package com.nami

import com.nami.assignments.y15.*
import com.nami.assignments.y23.Y23D01
import com.nami.assignments.y24.*
import java.nio.file.Files
import java.nio.file.Path

val assignments = listOf(
    Y15D01(),
    Y15D02(),
    Y15D03(),
    Y15D04(),
    Y15D05(),
    Y15D06(),
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

fun export() {
    val solutions = assignments.map { it.solve() }

    val sb = StringBuilder()
    sb.appendLine("|Year|Day|A|B|A Test|B Test|")
    sb.appendLine("|----|---|-|-|------|------|")
    solutions.forEach { solution ->
        sb.appendLine("|${solution.year}|${solution.day}|${solution.a}|${solution.b}|${solution.aTest}|${solution.bTest}|")
    }

    val markdown = sb.toString()
    Files.writeString(Path.of("solutions.md"), markdown)
}

val assignment = Y15D07()
fun main() {
    println(assignment.solve())
}