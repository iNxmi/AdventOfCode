package com.nami.assignments

import com.nami.Assignment
import kotlin.math.abs

class Y24D2 : Assignment<List<String>>{

    override fun year(): Int {
        return 2024
    }

    override fun day(): Int {
       return 2
    }

    override fun getProcessedInput(): List<String> {
        return getInput().lines()
    }

    override fun solveA(): Int {
        val lines = getProcessedInput()

        val valid = mutableSetOf<List<Int>>()
        val invalid = mutableSetOf<List<Int>>()
        for (line in lines) {
            val split = line.split(" ").map { it.toInt() }
            if (isAscending(split.toMutableList(), 0) || isDescending(split.toMutableList(), 0))
                valid.add(split)
            else
                invalid.add(split)
        }

        return valid.size
    }

    override fun solveB(): Int {
        val lines = getProcessedInput()

        val valid = mutableSetOf<List<Int>>()
        val invalid = mutableSetOf<List<Int>>()
        for (line in lines) {
            val split = line.split(" ").map { it.toInt() }.toMutableList()
            if (isAscending(split.toMutableList(), 1) || isDescending(split.toMutableList(), 1))
                valid.add(split)
            else
                invalid.add(split)
        }

        return valid.size
    }

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

}

fun main() {
    println(Y24D2().solveA())
    println(Y24D2().solveB())
}