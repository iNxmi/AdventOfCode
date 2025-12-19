package com.nami.aoc.task.solution.y25

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.max

class Y2025D05 : Task<Y2025D05.Database>(2025, 5) {

    data class Database(
        val ranges: Set<LongRange>,
        val ids: Set<Long>
    )

    override fun getProcessedInput(raw: String): Database {
        val split = raw.split("\n\n")
        val ranges = split[0].trim().lines().map { line ->
            val rangeSplit = line.split("-")
            LongRange(rangeSplit[0].toLong(), rangeSplit[1].toLong())
        }.toSet()

        val ids = split[1].trim().lines().map { line ->
            line.toLong()
        }.toSet()

        return Database(ranges, ids)
    }

    private fun merge(ranges: Set<LongRange>): Set<LongRange> {
        val sorted = ranges.sortedBy { it.first }

        val merged = mutableListOf<LongRange>()
        merged.add(sorted.first())

        for ((index, range) in sorted.withIndex()) {
            if (index == 0)
                continue

            val lastRange = merged[merged.lastIndex]
            if (lastRange.last >= range.first)
                merged[merged.lastIndex] = LongRange(lastRange.first, max(lastRange.last, range.last))
            else
                merged.add(range)
        }

        return merged.toSet()
    }

    override fun getPartA() = object : Part.A<Database>(task = this, bonus = 5.0) {
        override fun solve(input: Database) = input.ids.count { id -> input.ranges.any { range -> range.contains(id) } }
    }

    override fun getPartB() = object : Part.B<Database>(task = this, bonus = 10.0) {
        override fun solve(input: Database) = merge(input.ranges).sumOf { it.last - it.first + 1 }
    }

}

//fun main() = Y2025D05().getTestVerifications().print()
//fun main() = Y2025D05().getResults().print()
fun main() = Y2025D05().getVerifications().print()