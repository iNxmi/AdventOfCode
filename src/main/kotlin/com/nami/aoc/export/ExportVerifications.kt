package com.nami.aoc.export

import com.nami.aoc.export
import com.nami.aoc.format
import com.nami.aoc.task.Task
import java.nio.file.Paths

fun main() {
    val tasks = Task.getAll()

    tasks.withIndex()
        .flatMap { (index, task) ->
            println("${task.year}_${task.day.format("%02d")} (${index + 1}/${tasks.size})")
            task.getVerifications()
        }.toSet()
        .export(Paths.get("export/verifications.md"))
}


