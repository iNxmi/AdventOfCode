package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2025D03 : Task<Y2025D03.BankArray>(2025, 3) {

    data class BankArray(
        val banks: List<Bank>
    )

    data class Bank(
        val batteries: List<Int>
    )

    override fun getProcessedInput(raw: String) = BankArray(
        raw.lines().map { line ->
            Bank(line.map { char -> char.digitToInt() })
        }
    )

    fun getJoltage(array: BankArray, digits: Int): Long {
        var sum = 0L
        for (bank in array.banks) {
            val batteries = bank.batteries
            val selected = mutableListOf<Int>()

            var cursor = 0
            for (digit in 0..<digits) {

                var batteryIndex = cursor
                var battery = batteries[batteryIndex]
                for (index in (cursor + 1)..(batteries.size - digits + digit)) {
                    val current = batteries[index]
                    if (current > battery) {
                        battery = current
                        batteryIndex = index
                    }
                }

                selected.add(battery)
                cursor = batteryIndex + 1
            }

            sum += selected.joinToString("").toLong()
        }

        return sum
    }

    override fun getPartA() = object : Part.A<BankArray>(this) {
        override fun solve(input: BankArray) = getJoltage(input, 2)
    }

    override fun getPartB() = object : Part.B<BankArray>(this) {
        override fun solve(input: BankArray) = getJoltage(input, 12)
    }

}

//fun main() = Y2025D03().getTestVerifications().print()
//fun main() = Y2025D03().getResults().print()
fun main() = Y2025D03().getVerifications().print()