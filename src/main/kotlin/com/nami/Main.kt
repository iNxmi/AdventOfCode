package com.nami

import com.nami.assignments.y15.Y15D05
import com.nami.assignments.y15.Y15D06
import kotlinx.serialization.json.*

val assignments = listOf(
//    Y15D01(),
//    Y15D02(),
//    Y15D03(),
//    Y15D04(),
//    Y15D05(),
    Y15D06(),
//   Y23D01(),
//    Y24D01(),
//    Y24D02(),
//    Y24D03()
//    Y24D04()
//    Y24D05(),
//    Y24D06(),
//    Y24D07()
//    Y24D08()
//    Y24D11()
//    Y24D15()
)

val solutions = Assignment.getSolutions(assignments)

fun main() {
    solutions.forEach { println("${it.key} -> A(${it.value.a}|${it.value.aTest}) B(${it.value.b}|${it.value.bTest})") }
}