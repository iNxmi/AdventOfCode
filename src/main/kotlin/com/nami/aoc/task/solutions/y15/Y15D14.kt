package com.nami.aoc.task.solutions.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.Input
import com.nami.aoc.task.input.InputSimplex

class Y15D14 : Task<Set<Y15D14.Racer>>(2015, 14) {

    override fun getRawInputTest(): Input = InputSimplex(
        """
        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
        """.trimIndent()
    )

    data class Racer(
        val name: String,
        val speed: Int,
        val timeMove: Int,
        val timeRest: Int
    )

    data class RaceResult(val distance: Int, val points: Int)

    override fun getProcessedInput(raw: String): Set<Racer> {
        val string = raw
            .replace(("\\.*\\,*").toRegex(), "")
            .replace("can fly", ",")
            .replace("km/s for", ",")
            .replace("seconds but then must rest for", ",")
            .replace("seconds", "")
            .replace(" ", "")

        val result = mutableSetOf<Racer>()
        string.lines().forEach { line ->
            val split = line.split(",")

            val name = split[0]
            val speed = split[1].toInt()
            val timeMove = split[2].toInt()
            val timeRest = split[3].toInt()
            val racer = Racer(name, speed, timeMove, timeRest)

            result.add(racer)
        }

        return result
    }

    private fun race(racers: Set<Racer>, duration: Int): Map<Racer, RaceResult> {
        val distances = HashMap<Racer, Int>()
        val points = mutableMapOf<Racer, Int>()
        racers.forEach {
            distances[it] = 0
            points[it] = 0
        }

        for (time in 0..<duration) {
            for (racer in racers) {
                val sum = racer.timeMove + racer.timeRest
                val current = time % sum
                if (current < racer.timeMove)
                    distances[racer] = distances[racer]!! + racer.speed
            }

            val sorted = distances.entries.sortedByDescending { it.value }
            val distance = sorted.first().value
            distances.filter { it.value == distance }.forEach { points[it.key] = points[it.key]!! + 1 }
        }

        val result = mutableMapOf<Racer, RaceResult>()
        racers.forEach { result[it] = RaceResult(distances[it]!!, points[it]!!) }

        return result
    }

    override fun getPartA() = object : Part<Set<Racer>> {
        override fun solve(input: Set<Racer>) = race(input, 2503).maxOf { it.value.distance }
        override fun test(input: Set<Racer>) = race(input, 1000).maxOf { it.value.distance }
        override fun bonus() = 5.0
    }

    override fun getPartB() = object : Part<Set<Racer>> {
        override fun solve(input: Set<Racer>) = race(input, 2503).maxOf { it.value.points }
        override fun test(input: Set<Racer>) = race(input, 1000).maxOf { it.value.points }
        override fun bonus() = 10.0
    }

}

fun main() = Y15D14().printResults()