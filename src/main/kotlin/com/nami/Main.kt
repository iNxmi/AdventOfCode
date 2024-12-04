package com.nami

import com.nami.assignments.*;

val assignments = listOf(
//    Y15D01(),
//    Y15D02(),
//    Y15D03(),
//    Y15D04(),
//    Y15D05(),
//to do    Y23D01(),
//    Y24D01(),
//    Y24D02(),
//    Y24D03()
    Y24D04()
)

val solutions = Assignment.getSolutions(assignments)

fun main() {
    solutions.forEach { println(it) }
}