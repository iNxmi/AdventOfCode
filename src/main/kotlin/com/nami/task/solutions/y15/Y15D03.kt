package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.SubTask
import com.nami.task.Task
import com.nami.task.input.Input
import com.nami.task.input.InputSimplex

class Y15D03 : Task<String>(2015, 3) {

    override fun getRawInputTest(): Input {
        return InputSimplex("^v^v^v^v^v")
    }

    override fun getProcessedInput(raw: String): String {
        return raw
    }

    override fun getSubTaskA() = object : SubTask<String> {
        override fun solve(input: String): Any {
            var x = 0
            var y = 0

            val houses = mutableSetOf<Pair<Int, Int>>()
            houses.add(Pair(x, y))

            for (c in input.toCharArray()) {
                when (c) {
                    '<' -> x -= 1
                    '>' -> x += 1
                    '^' -> y += 1
                    'v' -> y -= 1
                }

                houses.add(Pair(x, y))
            }

            return houses.size
        }
        override fun bonus() = 5.0
    }

    override fun getSubTaskB() = object : SubTask<String> {
        override fun solve(input: String): Any {
            var xSanta = 0
            var ySanta = 0

            var xRobot = 0
            var yRobot = 0

            val houses = mutableSetOf<Pair<Int, Int>>()
            houses.add(Pair(0, 0))

            var santa = false
            for (c in input.toCharArray()) {
                if (santa) {
                    when (c) {
                        '<' -> xSanta -= 1
                        '>' -> xSanta += 1
                        '^' -> ySanta += 1
                        'v' -> ySanta -= 1
                    }
                } else {
                    when (c) {
                        '<' -> xRobot -= 1
                        '>' -> xRobot += 1
                        '^' -> yRobot += 1
                        'v' -> yRobot -= 1
                    }
                }

                houses.add(Pair(xSanta, ySanta))
                houses.add(Pair(xRobot, yRobot))

                santa = !santa
            }

            return houses.size
        }
        override fun bonus() = 10.0
    }

}

fun main() = Y15D03().getResult().println()
