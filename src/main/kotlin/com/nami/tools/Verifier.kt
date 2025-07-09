package com.nami.tools

import com.nami.task.Part
import com.nami.task.Task

class Verifier {
    companion object {
        val entries = verify()

        val failed = entries.filter { it.status == Part.Verification.Status.FAILED }.toSet()
        val unsolved = entries.filter { it.status == Part.Verification.Status.UNSOLVED }.toSet()
        val solved = entries.filter { it.status == Part.Verification.Status.SOLVED }.toSet()

        private fun verify() = Task.getAll().map { it.getVerifications() }.flatMap { setOf(it.first, it.second) }

    }
}