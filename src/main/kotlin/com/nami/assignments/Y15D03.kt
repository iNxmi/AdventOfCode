package com.nami.assignments

import com.nami.Assignment

class Y15D03 : Assignment<String>(2015, 3) {

    override fun getProcessedInput(raw: String): String {
        return raw
    }

    override fun solveA(input: String): Int {
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

    override fun solveB(input: String): Int {
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

}