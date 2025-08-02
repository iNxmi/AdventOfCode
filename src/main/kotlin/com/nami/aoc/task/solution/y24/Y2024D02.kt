package com.nami.aoc.task.solution.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.abs

class Y2024D02 : Task<List<String>>(2024, 2) {

    override fun getProcessedInput(raw: String) = raw.lines()

    private fun isAscending(list: MutableList<Int>, damp: Int): Boolean {
        if (damp < 0)
            return false

        for (i in 1..<list.size) {
            val n = list[i - 1]
            val m = list[i]

            if (m < n && damp <= 0)
                return false

            val diff = abs(m - n)
            if (diff !in 1..3 && damp <= 0)
                return false

            if (m < n || diff !in 1..3) {
                val listN = list.toMutableList()
                listN.removeAt(i - 1)

                val listM = list.toMutableList()
                listM.removeAt(i)

                val newDamp = damp - 1

                return isAscending(listN, newDamp) || isAscending(listM, newDamp)
            }
        }

        return true
    }

    private fun isDescending(list: MutableList<Int>, damp: Int): Boolean {
        if (damp < 0)
            return false

        for (i in (1..<list.size).reversed()) {
            val n = list[i - 1]
            val m = list[i]

            if (m > n && damp <= 0)
                return false

            val diff = abs(m - n)
            if (diff !in 1..3 && damp <= 0)
                return false

            if (m > n || diff !in 1..3) {
                val listN = list.toMutableList()
                listN.removeAt(i - 1)

                val listM = list.toMutableList()
                listM.removeAt(i)

                val newDamp = damp - 1

                return isDescending(listN, newDamp) || isDescending(listM, newDamp)
            }
        }

        return true
    }

    override fun getPartA() = object : Part<List<String>>(
        year, day, Type.A,
        bonus = 3.0
    ) {
        override fun solve(input: List<String>): Any {
            val valid = mutableSetOf<List<Int>>()
            val invalid = mutableSetOf<List<Int>>()
            for (line in input) {
                val split = line.split(" ").map { it.toInt() }
                if (isAscending(split.toMutableList(), 0) || isDescending(split.toMutableList(), 0))
                    valid.add(split)
                else
                    invalid.add(split)
            }

            return valid.size
        }
    }

    override fun getPartB() = object : Part<List<String>>(
        year, day, Type.B,
        bonus = 7.0
    ) {
        override fun solve(input: List<String>): Any {
            val valid = mutableSetOf<List<Int>>()
            val invalid = mutableSetOf<List<Int>>()
            for (line in input) {
                val split = line.split(" ").map { it.toInt() }.toMutableList()
                if (isAscending(split.toMutableList(), 1) || isDescending(split.toMutableList(), 1))
                    valid.add(split)
                else
                    invalid.add(split)
            }

            return valid.size
        }
    }

}