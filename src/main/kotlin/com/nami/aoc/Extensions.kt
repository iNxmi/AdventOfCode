package com.nami.aoc

import com.nami.aoc.task.Result
import com.nami.aoc.task.Verification
import net.steppschuh.markdowngenerator.table.Table
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.io.path.createParentDirectories
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

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

fun <T> Collection<T>.combinations(): List<List<T>> {
    val bits = this.size
    val limit = (2.0).pow(bits).roundToInt()

    return (0 until limit).mapNotNull { index ->
        val binary = Integer.toBinaryString(index)
            .padStart(bits, '0')
            .map { it.digitToInt() }

        this.withIndex().mapNotNull { (index, value) ->
            if (binary[index] == 1) value else null
        }
    }
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
            "${result.part.task.year}_${result.part.task.day}_${result.part.suffix}",
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
            "${result.part.task.year}_${result.part.task.day}_${result.part.suffix}",
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
        val format = DateTimeFormatter.ofPattern("YYYY/MM/dd - HH:mm:ss")
        val timestamp = LocalDateTime.now().format(format)
        appendLine("# Timestamp: $timestamp")

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
    addRow("#", "Year", "Day", "Part", "Expected", "Actual", "Time (s)", "Bonus (€)", "Comment")

    set.withIndex().forEach { (index, verification) ->
        addRow(
            index,
            verification.result.part.task.year,
            verification.result.part.task.day,
            verification.result.part.suffix,
            verification.expected,
            verification.result.value,
            verification.result.timeInSeconds.format("%.2fs"),
            verification.result.part.bonus?.format("%.2f€") ?: "",
            verification.result.part.comment
        )
    }
}.build().toString()

fun Number.format(format: String) = format.format(this)

fun Int.divisors() = this.toLong().divisors().map { it.toInt() }
fun Long.divisors(): Set<Long> {
    val sqrt = sqrt(this.toDouble()).toLong()
    val divisors = mutableSetOf<Long>()
    (1L..sqrt).forEach { i ->
        if (this % i == 0L) {
            divisors.add(i)

            val counterpart = this / i
            if (i != counterpart)
                divisors.add(counterpart)
        }
    }
    return divisors.toSet()
}

fun <T, R> Iterable<T>.mapMultithreaded(maxThreads: Int, timeoutMs: Long = 100, transform: (T) -> R): List<R> {
    val queue = this.mapTo(LinkedList()) { it }

    val concurrentDeque = ConcurrentLinkedDeque<R>()

    val threads = mutableSetOf<Thread>()
    while (queue.isNotEmpty()) {
        Thread.sleep(timeoutMs)

        val iterator = threads.iterator()
        while (iterator.hasNext()) {
            val thread = iterator.next()
            if (thread.state == Thread.State.TERMINATED)
                iterator.remove()
        }

        if (threads.size >= maxThreads)
            continue

        val item = queue.pop()
        val thread = Thread { concurrentDeque.addLast(transform(item)) }
        threads.add(thread)
        thread.start()
    }

    for (thread in threads)
        thread.join()

    return concurrentDeque.map { it }
}

fun <T, R> Iterable<T>.flatMapMultithreaded(maxThreads: Int, timeoutMs: Long = 100, transform: (T) -> Iterable<R>): List<R> {
    val queue = this.mapTo(LinkedList()) { it }

    val concurrentDeque = ConcurrentLinkedDeque<R>()

    val threads = mutableSetOf<Thread>()
    while (queue.isNotEmpty()) {
        Thread.sleep(timeoutMs)

        val iterator = threads.iterator()
        while (iterator.hasNext()) {
            val thread = iterator.next()
            if (thread.state == Thread.State.TERMINATED)
                iterator.remove()
        }

        if (threads.size >= maxThreads)
            continue

        val item = queue.pop()
        val thread = Thread { concurrentDeque.addAll(transform(item)) }
        threads.add(thread)
        thread.start()
    }

    for (thread in threads)
        thread.join()

    return concurrentDeque.map { it }
}

fun IntRange.length() = this.last - this.first + 1