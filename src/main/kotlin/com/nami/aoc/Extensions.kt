package com.nami.aoc

import com.nami.aoc.task.Result
import com.nami.aoc.task.Verification
import net.steppschuh.markdowngenerator.table.Table
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createParentDirectories

fun <T> List<T>.permutations(level: Int = 0): Set<List<T>> {
    val size = this.size

    if (size - level <= 1)
        return setOf(this)

    val result = mutableSetOf<List<T>>()
    for (i in level..<size)
        for (j in i..<size) {
            val swapped = this.toMutableList()
            swapped[i] = this[j]
            swapped[j] = this[i]

            val permutations = swapped.permutations(level + 1)
            result.addAll(permutations)
        }

    return result
}

@JvmName("printVerifications")
fun Set<Verification>.print() {
    val table = Table.Builder()
        .withAlignment(Table.ALIGN_RIGHT)
        .addRow("#", "ID", "Status", "Expected", "Actual", "Time (s)")

    this.withIndex().forEach { (index, verification) ->
        val result = verification.result
        table.addRow(
            index,
            "${result.part.task.year}_${result.part.task.day}_${result.part.type}",
            verification.status,
            verification.expected,
            result.value,
            result.timeInSeconds.format("%.2fs")
        )
    }

    println(table.build())
}

@JvmName("printResults")
fun Set<Result>.print() {
    val table = Table.Builder()
        .withAlignment(Table.ALIGN_RIGHT)
        .addRow("#", "ID", "Result", "Time (s)")

    this.withIndex().forEach { (index, result) ->
        table.addRow(
            index,
            "${result.part.task.year}_${result.part.task.day}_${result.part.type}",
            result.value,
            result.timeInSeconds.format("%.2fs")
        )
    }

    println(table.build())
}

fun Set<Verification>.export(path: Path) {
    val failed = this.filter { it.status == Verification.Status.FAILED }.toSet()
    val unsolved = this.filter { it.status == Verification.Status.UNSOLVED }.toSet()
    val solved = this.filter { it.status == Verification.Status.SOLVED }.toSet()

    val size = this.size
    val string = StringBuilder().apply {
        appendLine("# Failed (${failed.size} / $size)")
        appendLine(getTableVerification(failed))
        appendLine("# Unsolved  (${unsolved.size} / $size)")
        appendLine(getTableVerification(unsolved))
        appendLine("# Solved  (${solved.size} / $size)")
        appendLine(getTableVerification(solved))
    }.toString()

    path.createParentDirectories()
    Files.writeString(path, string)
}

private fun getTableVerification(set: Set<Verification>) = Table.Builder().apply {
    withAlignment(Table.ALIGN_RIGHT)
    addRow("#", "Year", "Day", "Part", "Expected", "Actual", "Time (s)", "Bonus (€)")

    set.withIndex().forEach { (index, verification) ->
        addRow(
            index,
            verification.result.part.task.year,
            verification.result.part.task.day,
            verification.result.part.type,
            verification.expected,
            verification.result.value,
            verification.result.timeInSeconds.format("%.2fs"),
            verification.result.part.bonus?.format("%.2f€") ?: ""
        )
    }
}.build().toString()

fun Number.format(format: String) = format.format(this)