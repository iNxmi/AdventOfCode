package com.nami.assignments.y24

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex

class Y24D11 : Assignment<Map<Long, Long>>(2024, 11) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex("125 17")
    }

    override fun getProcessedInput(raw: String): Map<Long, Long> {
        val map = mutableMapOf<Long, Long>()
        val list = raw.split(" ").map { it.toLong() }
        for (n in list)
            map[n] = list.count { it == n }.toLong()

        return map
    }

    private fun subtract(map: MutableMap<Long, Long>, key: Long, value: Long) {
        map[key] = map.getOrDefault(key, 0L) - value

        if (map[key] == 0L)
            map.remove(key)
    }

    private fun tick(map: Map<Long, Long>): Map<Long, Long> {
        val newMap = map.toMutableMap()
        for ((k, v) in map) {
            if (k == 0L) {
                newMap[1L] = newMap.getOrDefault(1L, 0L) + v
                subtract(newMap, k, v)
                continue
            }

            if (k.toString().length % 2 == 0) {
                val a = k.toString().substring(0, k.toString().length / 2).toLong()
                val b = k.toString().substring(k.toString().length / 2, k.toString().length).toLong()

                subtract(newMap, k, v)
                newMap[a] = newMap.getOrDefault(a, 0L) + v
                newMap[b] = newMap.getOrDefault(b, 0L) + v

                continue
            }

            subtract(newMap, k, v)
            val value = k * 2024L
            newMap[value] = newMap.getOrDefault(value, 0L) + v
        }

        return newMap
    }

    private fun solve(input: Map<Long, Long>, n: Int): Long {
        var map = input.toMutableMap()

        for (count in 0..<n)
            map = tick(map).toMutableMap()

        return map.values.sum()
    }

    override fun solveA(input: Map<Long, Long>): Long {
        return solve(input, 25)
    }

    override fun solveB(input: Map<Long, Long>): Long {
        return solve(input, 75)
    }

}

fun main() {
    println(Y24D11().solve())
}