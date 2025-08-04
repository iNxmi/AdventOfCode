package com.nami.aoc.export

import com.nami.aoc.DAY_RANGE
import com.nami.aoc.YEAR_RANGE
import com.nami.aoc.format
import com.nami.aoc.task.Task
import com.nami.aoc.task.Verification
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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
        }.toSet()

    val width = DAY_RANGE.endInclusive - DAY_RANGE.start + 1
    val height = YEAR_RANGE.endInclusive - YEAR_RANGE.start + 1
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics() as Graphics2D
    verifications.forEach { verification ->
        val year = verification.result.part.task.year
        val day = verification.result.part.task.day

        val color = when (verification.status) {
            Verification.Status.FAILED -> Color(1f, 0f, 0f, 0.5f)
            Verification.Status.SOLVED -> Color(0f, 1f, 0f, 0.5f)
            Verification.Status.UNSOLVED -> Color(0f, 0f, 1f, 0.5f)
        }

        graphics.color = color
        graphics.fillRect(day - 1, year - 2015, 1, 1)
    }
    graphics.dispose()

    ImageIO.write(image, "PNG", File("export/coverage.png"))
}


