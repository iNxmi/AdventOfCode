package com.nami.y2023

import com.nami.Assignment
import java.util.List

class D01 : Assignment() {

    override fun year(): Int {
        return 2023
    }

    override fun day(): Int {
        return 1
    }

    override fun solveA(input: String): Int {
        val numbers: MutableList<Int> = ArrayList()

        val lines = input.split(("\n").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            val chars = line.toCharArray()

            val digits: MutableList<Int> = ArrayList()
            for (c: Char in chars)
                if (c.isDigit())
                    digits.add(c.digitToIntOrNull()!!)

            numbers.add(digits.first * 10 + digits.last)
        }

        return numbers.sum()
    }

    override fun solveB(input: String): Int {
        TODO("Not yet implemented")
    }

}

fun main() {
    println(D01().solveA())
}