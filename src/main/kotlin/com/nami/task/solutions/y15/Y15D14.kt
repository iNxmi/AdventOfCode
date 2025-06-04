package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.test.TestInput
import com.nami.task.test.TestInputSimplex

class Y15D14 : Task<Set<Y15D14.Racer>>(2015, 14) {

    override fun getRawInputTest(): TestInput = TestInputSimplex(
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

    override fun getSubTaskA() = object : SubTask<Set<Racer>> {
        override fun solve(input: Set<Racer>) = race(input, 2503).maxOf { it.value.distance }
        override fun test(input: Set<Racer>) = race(input, 1000).maxOf { it.value.distance }
    }

    override fun getSubTaskB() = object : SubTask<Set<Racer>> {
        override fun solve(input: Set<Racer>) = race(input, 2503).maxOf { it.value.points }
        override fun test(input: Set<Racer>) = race(input, 1000).maxOf { it.value.points }
    }

}

fun main() = Y15D14().getResult().println()