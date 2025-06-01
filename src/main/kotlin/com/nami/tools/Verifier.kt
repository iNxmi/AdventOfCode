package com.nami.tools

import com.nami.task.Remote
import com.nami.task.Task

enum class Part { A, B }

data class Entry(
    val id: Int, val year: Int, val day: Int,
    val part: Part,
    val task: String?,
    val remote: String?,
) {
    fun failed(): Boolean = task != null && remote != null && task != remote
    fun unsolved(): Boolean = task == null || remote == null
    fun solved(): Boolean = task != null && remote != null && task == remote
}

class Verifier {
    companion object {

        val entries = verify()

        val failed = entries.filter { it.failed() }.toSet()
        val unsolved = entries.filter { it.unsolved() }.toSet()
        val solved = entries.filter { it.solved() }.toSet()

        private fun verify(): Set<Entry> {
            val tasks = Task.getAll()
            val entries = tasks.flatMap { task ->
                val result = task.solve()

                val resultATask = result.a.toString()
                val resultARemote = Remote.getSolutionA(task.year, task.day)
                val a = Entry(task.id, task.year, task.day, Part.A, resultATask, resultARemote)

                val resultBTask = result.b.toString()
                val resultBRemote = Remote.getSolutionB(task.year, task.day)
                val b = Entry(task.id, task.year, task.day, Part.B, resultBTask, resultBRemote)

                setOf(a, b)
            }.toSet()

            return entries
        }

    }
}