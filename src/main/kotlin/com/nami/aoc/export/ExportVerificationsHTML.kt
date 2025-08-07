package com.nami.aoc.export

import com.nami.aoc.DAY_RANGE
import com.nami.aoc.YEAR_RANGE
import com.nami.aoc.format
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.Verification
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round

fun main() {
    val timeStart = System.nanoTime()

    val tasks = Task.getAll()
    val verifications = tasks.withIndex()
        .flatMap { (index, task) ->
            println("${task.year}_${task.day.format("%02d")} (${index + 1}/${tasks.size})")
            task.getVerifications()
        }

    val status = mutableMapOf<Int, String>()
    for (verification in verifications) {
        val day = verification.result.part.task.day
        val year = verification.result.part.task.year
        val suffix = if (verification.result.part.suffix == Part.Suffix.A) 0 else 1

        val string = if (verification.status == Verification.Status.FAILED) {
            "red"
        } else if (verification.status == Verification.Status.SOLVED && verification.result.part.bonus == null) {
            "yellow"
        } else if (verification.status == Verification.Status.SOLVED) {
            "green"
        } else continue

        status[year * 1000 + day * 10 + suffix] = string
    }

    val stars = mutableMapOf<Int, Int>()
    verifications.forEach { verification ->
        val year = verification.result.part.task.year
        if (verification.status == Verification.Status.SOLVED || verification.status == Verification.Status.FAILED)
            stars[year] = stars.getOrDefault(year, 0) + 1
    }

    val starsSum = stars.values.sum()

    val formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd")
    val date = LocalDate.now().format(formatter)

    val years = YEAR_RANGE.map { it }.reversed().toSet()
    val days = DAY_RANGE.map { it }.toSet()

    val resolver = ClassLoaderTemplateResolver()
    resolver.prefix = "templates/"
    resolver.suffix = ".html"
    resolver.templateMode = TemplateMode.HTML
    resolver.characterEncoding = "UTF-8"

    val engine = TemplateEngine()
    engine.setTemplateResolver(resolver)

    val context = Context()
    context.setVariable("years", years)
    context.setVariable("days", days)
    context.setVariable("stars", stars)
    context.setVariable("starsSum", starsSum)
    context.setVariable("status", status)
    context.setVariable("date", date)
    context.setVariable("time", round((System.nanoTime() - timeStart) * 1E-9 * 100.0) / 100.0)

    val result = engine.process("verifications", context)
    Files.writeString(Paths.get("export/verifications.html"), result)
}