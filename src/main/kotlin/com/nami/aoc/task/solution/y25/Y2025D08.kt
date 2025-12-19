package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.y25.Y2025D06.Problem
import org.joml.Vector2i
import org.joml.Vector3i
import java.lang.Math.pow
import java.text.NumberFormat
import kotlin.math.pow

class Y2025D08 : Task<Set<Vector3i>>(2025, 8) {

    override fun getProcessedInput(raw: String) = raw.lines().map {
        val split = it.split(",")

        val x = split[0].toInt()
        val y = split[1].toInt()
        val z = split[2].toInt()

        Vector3i(x, y, z)
    }.toSet()

    override fun getPartA() = object : Part.A<Set<Vector3i>>(this) {
        override fun solve(input: Set<Vector3i>):Long {
            input.forEach {
                println(it.toString(NumberFormat.getNumberInstance()))
            }
            println(input.size)
            return  0
        }
    }

    override fun getPartB() = object : Part.B<Set<Vector3i>>(this) {
        override fun solve(input: Set<Vector3i>) = null
    }

}

//fun main() = Y2025D08().getTestVerifications().print()
fun main() = Y2025D08().getResults().print()
//fun main() = Y2025D08().getVerifications().print()