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
        .addRow("#", "ID", "Status", "Expected", "Actual", "Time (s)")

    this.withIndex().forEach { (index, verification) ->
        val result = verification.result
        table.addRow(
            index,
            "${result.year}_${result.day}_${result.part}",
            verification.status,
            verification.expected,
            result.value,
            result.timeInSeconds
        )
    }

    println(table.build())
}

@JvmName("printResults")
fun Set<Result>.print() {
    val table = Table.Builder()
        .addRow("#", "ID", "Result", "Time (s)")

    this.withIndex().forEach { (index, result) ->
        table.addRow(
            index,
            "${result.year}_${result.day}_${result.part}",
            result.value,
            result.timeInSeconds
        )
    }

    println(table.build())
}

fun Set<Verification>.export(path: Path) {
    val failed = this.filter { it.status == Verification.Status.FAILED }.toSet()
    val unsolved = this.filter { it.status == Verification.Status.UNSOLVED }.toSet()
    val solved = this.filter { it.status == Verification.Status.SOLVED }.toSet()

    val builder = StringBuilder()
    builder.appendLine("# Failed (${failed.size} / ${this.size})")
    builder.appendLine(getTableVerification(failed))
    builder.appendLine("# Unsolved  (${unsolved.size} / ${this.size})")
    builder.appendLine(getTableVerification(unsolved))
    builder.appendLine("# Solved  (${solved.size} / ${this.size})")
    builder.appendLine(getTableVerification(solved))

    path.createParentDirectories()
    Files.writeString(path, builder.toString())
}

private fun getTableVerification(set: Set<Verification>) = Table.Builder().apply {
    withAlignments(
        Table.ALIGN_RIGHT,
        Table.ALIGN_RIGHT,
        Table.ALIGN_RIGHT,
        Table.ALIGN_LEFT,
        Table.ALIGN_RIGHT,
        Table.ALIGN_RIGHT,
        Table.ALIGN_RIGHT,
        Table.ALIGN_RIGHT,
        Table.ALIGN_LEFT
    )
    addRow("#", "Year", "Day", "Part", "Expected", "Actual", "Time (s)", "Bonus (€)", "URL")

    set.withIndex().forEach { indexed ->
        val value = indexed.value
        addRow(
            indexed.index,
            value.result.year,
            value.result.day,
            value.result.part,
            value.expected,
            value.result.value,
            ("%.2fs").format(value.result.timeInSeconds),
//                    if (it.part.bonus() != null) ("%.2f€").format(it.part.bonus()) else "",
//                    Link(it.task.url)
        )
    }
}.build().toString()