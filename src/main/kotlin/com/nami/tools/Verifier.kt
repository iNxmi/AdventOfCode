package com.nami.tools

import com.nami.task.Remote
import com.nami.task.Task

enum class Part { A, B }

data class Entry(
    val id: Int, val year: Int, val day: Int,
    val part: Part,
    val task: Any?,
    val remote: String?,
    val timeS: Double,
    val bonus: Double?
) {
    fun failed() = (remote != null && task.toString() != remote)
    fun unsolved() = (remote == null)
    fun solved() = (task != null && remote != null && task.toString() == remote)
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
                val result = task.getResult()

                val resultARemote = Remote.getSolutionA(task.year, task.day)
                val a = Entry(
                    task.id, task.year, task.day,
                    Part.A,
                    result.a.result,
                    resultARemote,
                    result.a.timeS,
                    task.getSubTaskA().bonus()
                )

                val resultBRemote = Remote.getSolutionB(task.year, task.day)
                val b = Entry(
                    task.id, task.year, task.day,
                    Part.B,
                    result.b.result,
                    resultBRemote,
                    result.b.timeS,
                    task.getSubTaskB().bonus()
                )

                setOf(a, b)
            }.toSet()

            return entries
        }

    }
}