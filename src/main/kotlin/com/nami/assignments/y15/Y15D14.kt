package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y15D14 : Assignment<Set<Y15D14.Racer>, Int>(2015, 14) {

    override fun getRawTestInput(): TestInput = TestInputSimplex(
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

    data class RaceResult(val distance: Int, val points: Int)
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

    override fun solveA(input: Set<Racer>): Int = race(input, 2503).maxOf { it.value.distance }
    override fun solveATest(input: Set<Racer>): Int = race(input, 1000).maxOf { it.value.distance }

    override fun solveB(input: Set<Racer>): Int = race(input, 2503).maxOf { it.value.points }
    override fun solveBTest(input: Set<Racer>): Int = race(input, 1000).maxOf { it.value.points }

}

fun main() = println(Y15D14().solve())