package com.nami.aoc.export

import com.nami.aoc.export
import com.nami.aoc.task.Task
import java.nio.file.Paths

fun main() = Task.getAll()
    .flatMap { it.getVerifications() }
    .toSet()
    .export(Paths.get("export/verifications.md"))