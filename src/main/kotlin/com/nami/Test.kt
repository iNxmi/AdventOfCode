package com.nami

import j2html.Config
import j2html.TagCreator.*
import j2html.rendering.HtmlBuilder
import j2html.rendering.IndentedHtml

val title = "FAILED"
val elements = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

fun test() {
    val html =
        body(
            table(
                tbody(
                    each(elements) { i ->
                        tr(
                            each(elements) { j ->
                                td(
                                    (i * j).toString()
                                )
                            }
                        )
                    }
                )
            )
        ).render(IndentedHtml.into(StringBuilder(), Config.global()) as HtmlBuilder<*>)

    html.println()
}

fun main() = test()