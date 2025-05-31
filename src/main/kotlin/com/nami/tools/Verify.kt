package com.nami.tools

import com.nami.task.Remote
import com.nami.task.Task
import java.nio.file.Files
import java.nio.file.Paths

enum class Part { A, B }

data class Entry(
    val id: Int, val year: Int, val day: Int,
    val part: Part,
    val task: String?,
    val remote: String?,
) {
    fun failed(): Boolean = task != null && remote != null && task != remote
    fun unsolved(): Boolean = task == null || remote == null
    fun success(): Boolean = task != null && remote != null && task == remote
}

fun verify() {
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
    }

    val failed = entries.filter { it.failed() }
    val unsolved = entries.filter { it.unsolved() }
    val success = entries.filter { it.success() }

    val builder = StringBuilder()

    builder.appendLine("# Failed (${failed.size})")
    builder.appendLine("|Year|Day|Part|Task|Remote|")
    builder.appendLine("|----|---|----|----|------|")
    failed.sortedBy { it.id }
        .map { it }
        .forEach { e ->
            builder.appendLine("|${e.year}|${e.day}|${e.part}|${e.task}|${e.remote}|")
        }

    builder.appendLine("# Unsolved (${unsolved.size})")
    builder.appendLine("|Year|Day|Part|Task|Remote|")
    builder.appendLine("|----|---|----|----|------|")
    unsolved.sortedBy { it.id }
        .map { it }
        .forEach { e ->
            builder.appendLine("|${e.year}|${e.day}|${e.part}|${e.task}|${e.remote}|")
        }

    builder.appendLine("# Success (${success.size})")
    builder.appendLine("|Year|Day|Part|Task|Remote|")
    builder.appendLine("|----|---|----|----|------|")
    success.sortedBy { it.id }
        .map { it }
        .forEach { e ->
            builder.appendLine("|${e.year}|${e.day}|${e.part}|${e.task}|${e.remote}|")
        }

    val markdown = builder.toString()
    Files.writeString(Paths.get("verification.md"), markdown)
}

fun main() = verify()