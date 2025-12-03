package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import sun.swing.MenuItemLayoutHelper.max
import kotlin.math.min

class Y2025D03 : Task<List<Y2025D03.Bank>>(2025, 3) {

    data class Bank(
        val batteries: List<Int>
    )

    override fun getProcessedInput(raw: String) =
        raw.lines().map { line -> Bank(line.map { char -> char.digitToInt() }) }

    fun getJoltage(banks: List<Bank>, digits: Int): Long {
        var sum = 0L
        for (bank in banks) {

            val batteries = bank.batteries

            val selectedBatteries = mutableListOf<Int>()
            val selectedIndices = mutableListOf<Int>()

            var cursor = 0
            for (digit in 0..<digits) {

                var selectedIndex = cursor
                var selected = batteries[selectedIndex]
                for (index in (cursor + 1)..(batteries.size - digits + digit)) {
                    val current = batteries[index]
                    if (current > selected) {
                        selected = current
                        selectedIndex = index
                    }
                }

                selectedBatteries.add(selected)
                selectedIndices.add(selectedIndex)

                println("$selectedIndex=$selected ")

                cursor = selectedIndex + 1
            }

            sum += selectedBatteries.joinToString("").toLong()


//            val batteries = bank.batteries
//
//            var a = batteries[0]
//            var aIndex = 0
//            for (i in 1..<batteries.size - 1) {
//                val current = bank.batteries[i]
//
//                if (current > a) {
//                    a = current
//                    aIndex = i
//                }
//            }
//
//            var b = batteries[aIndex + 1]
//            for (i in (aIndex + 1)..<batteries.size) {
//                val current = bank.batteries[i]
//
//                if (current > b)
//                    b = current
//            }
//
//            sum += "$a$b".toInt()
        }

        return sum
    }

    override fun getPartA() = object : Part.A<List<Bank>>(this) {
        override fun solve(input: List<Bank>) = getJoltage(input, 2)
    }

    override fun getPartB() = object : Part.B<List<Bank>>(this) {
        override fun solve(input: List<Bank>) = getJoltage(input, 12)
    }

}

//fun main() = Y2025D03().getTestVerifications().print()
fun main() = Y2025D03().getResults().print()
//fun main() = Y2025D03().getVerifications().print()