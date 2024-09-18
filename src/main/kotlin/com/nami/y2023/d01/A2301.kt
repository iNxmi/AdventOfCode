package com.nami.y2023.d01

import com.nami.Assignment
import com.nami.Input
import java.util.ArrayList

class PartA : Assignment(2023, 1) {

    override fun solve(input: String): Int {
        var input = Input.fetch(2023, 1)
        val numbers: MutableList<Int> = mutableListOf()

        val lines = input.split("\n")
        for (line in lines) {
            val chars = line.toCharArray()

            val digits: MutableList<Int> = ArrayList()
            for (c in chars)
                if (c.isDigit())
                    digits.add(c.digitToIntOrNull()!!)

            numbers.add(digits.first() * 10 + digits.last())
        }

        return numbers.sum()
    }

}

fun main() {
    println(PartA().solve())
}