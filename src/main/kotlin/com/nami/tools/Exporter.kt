package com.nami.tools

import j2html.TagCreator.*
import java.nio.file.Files
import java.nio.file.Paths

class Exporter {
    companion object {
        fun verification() {
            val builder = StringBuilder()
            builder.appendLine(getTableVerificationHTML("Failed", Verifier.failed))
            builder.appendLine(getTableVerificationHTML("Unsolved", Verifier.unsolved))
            builder.appendLine(getTableVerificationHTML("Solved", Verifier.solved))

            val markdown = builder.toString()
            Files.writeString(Paths.get("summary.md"), markdown)
        }

        private fun getTableVerificationHTML(title: String, content: Set<Entry>): String {
            val years = mutableMapOf<Int, Map<Int, List<Entry>>>()
            content.groupBy { it.year }.forEach { (key, value) ->
                years[key] = value.groupBy { it.day }
            }

            val html =
                div(
                    style("td, th, tr {text-align: center;}"),
                    h1("$title (${content.size})"),
                    table().with(
                        thead().with(
                            th("Year"),
                            th("Day"),
                            th("Part"),
                            th("Task"),
                            th("Remote"),
                            th("Time (s)"),
                            th("Bonus (€)")
                        ),
                        tbody().with(
                            years.entries.flatMap { (year, day) ->
                                val yearRowCount = day.values.sumOf { it.size }
                                var yearPrinted = false

                                day.entries.flatMap { (day, events) ->
                                    val dayRowCount = events.size
                                    var dayPrinted = false

                                    events.mapIndexed { _, event ->
                                        tr().with(
                                            if (!yearPrinted) {
                                                yearPrinted = true
                                                td(year.toString()).attr("rowspan", yearRowCount.toString())
                                            } else null,
                                            if (!dayPrinted) {
                                                dayPrinted = true
                                                td(day.toString()).attr("rowspan", dayRowCount.toString())
                                            } else null,
                                            td(event.part.toString()),
                                            if (event.task != null) td(event.task.toString()) else td(),
                                            if (event.remote != null) td(event.remote.toString()) else td(),
                                            td(("%.4fs").format(event.timeS).replace(",", ".")),
                                            if (event.bonus != null) td(
                                                ("%.2f€").format(event.bonus).replace(",", ".")
                                            ) else td()
                                        )
                                    }
                                }
                            }
                        )
                    )
                )

            return html.renderFormatted()
        }


    }
}

fun main() {
    Exporter.verification()
}