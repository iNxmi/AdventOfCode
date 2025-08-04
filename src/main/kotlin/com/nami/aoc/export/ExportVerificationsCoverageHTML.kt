package com.nami.aoc.export

import com.nami.aoc.DAY_RANGE
import com.nami.aoc.YEAR_RANGE
import com.nami.aoc.format
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.Verification
import j2html.TagCreator.*
import org.joml.Vector3i
import java.nio.file.Files
import java.nio.file.Paths

//TODO experimental, make it export to an interactable html document
fun main() {
    // X-Axis = Day
    // Y-Axis = Year
    // Z-Axis (Brightness / Color) = Status

    val tasks = Task.getAll()
    val verifications = tasks.withIndex()
        .flatMap { (index, task) ->
            println("${task.year}_${task.day.format("%02d")} (${index + 1}/${tasks.size})")
            task.getVerifications()
        }

    val map = mutableMapOf<Vector3i, String>()
    verifications.forEach { verification ->
        val x = verification.result.part.task.day
        val y = verification.result.part.task.year
        val z = if (verification.result.part.suffix == Part.Suffix.A) 0 else 1

        val string = when (verification.status) {
            Verification.Status.SOLVED -> "solved"
            Verification.Status.UNSOLVED -> "unsolved"
            Verification.Status.FAILED -> "failed"
        }

        map[Vector3i(x, y, z)] = string
    }

    val progress = mutableMapOf<Int, Int>()
    verifications.forEach { verification ->
        val year = verification.result.part.task.year
        if (verification.status == Verification.Status.SOLVED || verification.status == Verification.Status.FAILED)
            progress[year] = progress.getOrDefault(year, 0) + 1
    }

    val years = buildList {
        for (year in YEAR_RANGE)
            add(year)
    }.reversed()
    val days = buildList {
        for (day in DAY_RANGE)
            add(day)
    }

    val html = html(
        head(
            title("ExportVerificationCoverageHTML"),
            style(
                """
                html            { font-family: arial; background: rgb(56,56,56); }
                .content        { max-width: fit-content; margin-left: auto; margin-right: auto; display: flex; gap: 30px; flex-direction: column; }
                .year           { display: flex; gap: 5px; }
                .year-content   { display: flex; gap: 0px; flex-direction: column; border: 1px solid black; }
                .day            { display: flex; flex-direction: column; aspect-ratio: 1 / 1; justify-content: space-evenly; color: white; text-decoration: none; }
                .part           { flex: 1; color: transparent; text-align: center; user-select: none; padding: 5px; text-wrap: nowrap; }
                .part:hover     { color: white; }
                .solved         { background: green; }
                .solved:hover   { background: darkgreen; }
                .unsolved       { background: rgb(40,40,40); }
                .unsolved:hover { background: rgb(32,32,32); }
                .failed         { background: red; }
                .failed:hover   { background: darkred; }
                .progress       { min-width: fit-content; background: rgb(72,72,72); border: none; }
                .title          { min-width: fit-content; background: rgb(72,72,72); color: white; text-align: center; padding: 5px; }
            """.trimIndent()
            )
        ),
        body(
            div(
                each(years) { year ->
                    div(
                        a(
                            "$year (${progress.getOrDefault(year, 0)} / 50)"
                        ).withHref("https://adventofcode.com/$year").withTarget("_blank").withClass("title"),
                        div(
                            each(days) { day ->
                                a(
                                    div("$year ${day.format("%02d")} A").withClasses(
                                        "part",
                                        map.getOrDefault(Vector3i(day, year, 0), "unsolved")
                                    ),
                                    div("$year ${day.format("%02d")} B").withClasses(
                                        "part",
                                        map.getOrDefault(Vector3i(day, year, 1), "unsolved")
                                    )
                                ).withClass("day")
                                    .withHref("https://adventofcode.com/$year/day/$day")
                                    .withTarget("_blank")
                            }
                        ).withClass("year"),
                        progress()
                            .withValue(progress.getOrDefault(year, 0).toString())
                            .withMax("50")
                            .withClass("progress")
                    ).withClass("year-content")
                }
            ).withClass("content")
        )
    )

    Files.writeString(Paths.get("export/verifications.html"), html.renderFormatted())
}


