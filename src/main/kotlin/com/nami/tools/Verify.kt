package com.nami.tools

import com.nami.task.Remote
import com.nami.task.solutions.y15.*
import com.nami.task.solutions.y23.Y23D01
import com.nami.task.solutions.y24.*

fun verify() {
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
        Y15D15(),
        Y15D16(),

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

    assignments.forEach { assignment ->
        val solution = assignment.solve()

        val solutionA = Remote.getSolutionA(assignment.year, assignment.day)
        if (solutionA == solution.a.toString())
            println("id=${assignment.id} a=${solution.a} solution_a=${solutionA}")
        else
            System.err.println("id=${assignment.id} a=${solution.a} solution_a=${solutionA}")

        val solutionB = Remote.getSolutionB(assignment.year, assignment.day)
        if (solutionB == solution.b.toString())
            println("id=${assignment.id} b=${solution.b} solution_b=${solutionB}")
        else
            System.err.println("id=${assignment.id} b=${solution.b} solution_b=${solutionB}")
    }
}

fun main() = verify()