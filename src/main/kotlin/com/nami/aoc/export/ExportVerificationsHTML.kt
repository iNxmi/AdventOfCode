package com.nami.aoc.export

import com.nami.aoc.DAY_RANGE
import com.nami.aoc.YEAR_RANGE
import com.nami.aoc.flatMapMultithreaded
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.Verification
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.awt.Desktop
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round

private fun getVerifications(maxThreads: Int = 4, timeoutMs: Long = 25L): Set<Verification> {
    val tasks = Task.getAll()
    val verifications = tasks.flatMapMultithreaded(maxThreads, timeoutMs) { task ->
        task.getVerifications()
    }.toSet()

    return verifications
}

private const val css = """
    html                            { font-family: \"Source Code Pro\", monospace; background: rgb(15, 15, 35); font-size: 24px; }
    a                               { color: #009900; text-decoration: none; }
    a:hover, a:focus                { color: #99ff99; }
    body                            { margin: 0; display: flex; justify-content: space-evenly; }
            
    .title                          { background: #ffffff11; text-align: center; padding: 5px; user-select: none; }
    .content                        { display: inline-flex; flex-direction: column; gap: 50px; justify-content: space-evenly; padding: 50px; }
    .ascii-art                      { display: flex; flex-direction: column; white-space: preserve-spaces;  color: #009900; text-align: center; user-select: none; }
    .year                           { display: flex; flex-direction: column; gap: 5px; }
    .days                           { display: flex; gap: 5px; }
    .day                            { display: flex; flex-direction: column; width: 100px; height: 100px; justify-content: space-evenly; gap: 5px; }
    .part                           { display: flex; flex-direction: column; justify-content: space-evenly; background: #ffffff22; flex-grow: 1; color: transparent; text-align: center; user-select: none; }
    .part:hover, .part:focus        { background: #ffffff33; color: #009900; text-shadow: black 0 0 5px;  }
    .green                          { background: #0099007f; }
    .green:hover, .green:focus      { background: #0099008f; }
    .yellow                         { background: #F2DF447f; }
    .yellow:hover, .yellow:focus    { background: #F2DF448f; }
    .red                            { background: #ff00007f; }
    .red:hover, .red:focus          { background: #ff00008f; }
            
    .progress-bar                   { background: #ffffff11; border: none; }
            
    .footer                         { display: flex; color: #009900; justify-content: center; gap: 1em; }
"""

private fun getHTML(threads: Int): String {
    val timeStart = System.nanoTime()

    val verifications = getVerifications(maxThreads = threads)

    val stars = mutableMapOf<Int, Int>()
    verifications.forEach { verification ->
        val year = verification.result.part.task.year
        if (verification.status == Verification.Status.SOLVED || verification.status == Verification.Status.FAILED)
            stars[year] = stars.getOrDefault(year, 0) + 1
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

    return createHTML().html {
        head { style { unsafe { +css } } }
        body {
            div(classes = "content") {
                a(classes = "ascii-art", href = "https://adventofcode.com/", target = "_blank") {
                    span { +"  /$$$$$$         /$$                                     /$$                        /$$$$$$          /$$$$$$                    /$$           " }
                    span { +" /$\$__  $$       | $$                                    | $$                       /$\$__  $$        /$\$__  $$                  | $$           " }
                    span { +"| $$  \\ $$   /$$$$$$$  /$$    /$$  /$$$$$$   /$$$$$$$   /$$$$$$           /$$$$$$  | $$  \\__/       | $$  \\__/   /$$$$$$    /$$$$$$$   /$$$$$$ " }
                    span { +"| $$$$$$$$  /$\$__  $$ |  $$  /$$/ /$\$__  $$ | $\$__  $$ |_  $\$_/          /$\$__  $$ | $$$$           | $$        /$\$__  $$  /$\$__  $$  /$\$__  $$" }
                    span { +"| $\$__  $$ | $$  | $$  \\  $$/$$/ | $$$$$$$$ | $$  \\ $$   | $$           | $$  \\ $$ | $\$_/           | $$       | $$  \\ $$ | $$  | $$ | $$$$$$$$" }
                    span { +"| $$  | $$ | $$  | $$   \\  $$$/  | $\$_____/ | $$  | $$   | $$ /$$       | $$  | $$ | $$             | $$    $$ | $$  | $$ | $$  | $$ | $\$_____/" }
                    span { +"| $$  | $$ |  $$$$$$$    \\  $/   |  $$$$$$$ | $$  | $$   |  $$$$/       |  $$$$$$/ | $$             |  $$$$$$/ |  $$$$$$/ |  $$$$$$$ |  $$$$$$$" }
                    span { +"|__/  |__/  \\_______/     \\_/     \\_______/ |__/  |__/    \\___/          \\______/  |__/              \\______/   \\______/   \\_______/  \\_______/" }
                }

                val years = YEAR_RANGE.map { it }.reversed().toSet()
                val days = DAY_RANGE.map { it }.toSet()
                for (year in years) div(classes = "year") {
                    val star = stars.getOrDefault(year, 0)
                    a(classes = "title", href = "https://adventofcode.com/$year", target = "_blank") {
                        +"[$year] ($star/50 - ${star * 2}%)"
                    }

                    div(classes = "days") {
                        for (day in days) div(classes = "day") {
                            a(
                                classes = "part ${status.getOrDefault(year * 1000 + day * 10, "")}",
                                href = "https://adventofcode.com/$year/day/$day",
                                target = "_blank"
                            ) { +"[${day}A]" }
                            a(
                                classes = "part ${status.getOrDefault(year * 1000 + day * 10 + 1, "")}",
                                href = "https://adventofcode.com/$year/day/$day#part2",
                                target = "_blank"
                            ) { +"[${day}B]" }
                        }
                    }

                    progress(classes = "progress-bar") {
                        value = "$star"
                        max = "50"
                    }
                }

                div(classes = "footer") {
                    span { +"[Memphis Herghelegiu]" }

                    val formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd")
                    val date = LocalDate.now().format(formatter)
                    span { +"[$date]" }

                    val time = round((System.nanoTime() - timeStart) * 1E-9 * 100.0) / 100.0
                    span { +"[${time}s - $threads Threads]" }

                    val sum = stars.values.sum()
                    val limit = years.size * 25 * 2
                    val percent = sum.toDouble() / limit.toDouble() * 100.0
                    span { +"[$sum/$limit - $percent%]" }
                }
            }
        }
    }
}

fun main() {
    val threads = 4
    val html = getHTML(threads)

    val path = Paths.get("export/verifications.html")
    Files.writeString(path, html)

    Desktop.getDesktop().open(path.toFile())
}