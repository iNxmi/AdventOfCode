package com.nami.y2023.d01

import com.nami.Assignment
import java.util.*

class B2301 : Assignment(2023, 1) {

    private val digits = mapOf(
        Pair("0", 0),
        Pair("1", 1),
        Pair("2", 2),
        Pair("3", 3),
        Pair("4", 4),
        Pair("5", 5),
        Pair("6", 6),
        Pair("7", 7),
        Pair("8", 8),
        Pair("9", 9),

        Pair("zero", 0),
        Pair("one", 1),
        Pair("two", 2),
        Pair("three", 3),
        Pair("four", 4),
        Pair("five", 5),
        Pair("six", 6),
        Pair("seven", 7),
        Pair("eight", 8),
        Pair("nine", 9),
    )

    override fun solve(input: String): Int {
        val numbers: MutableList<Int> = ArrayList()

        val lines = input.split("\n")
        for(line in lines) {
            val map = TreeMap<Int, Int>()

            digits.keys.forEach { d ->
                var index = line.indexOf(d)
                if(index != -1)
                    map[index] = digits[d]!!

                index = line.lastIndexOf(d)
                if(index != -1)
                    map[index] = digits[d]!!
            }

            numbers.add(map.firstEntry().value * 10 + map.lastEntry().value)
        }

        return numbers.sum()
    }

}

fun main() {
    println(B2301().solve())
}