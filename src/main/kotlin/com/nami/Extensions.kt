package com.nami

import kotlin.math.pow
import kotlin.math.round

fun <T> List<T>.permutations(level: Int = 0): Set<List<T>> {
    val size = this.size

    if (size - level <= 1)
        return setOf(this)

    val result = mutableSetOf<List<T>>()
    for (i in level..<size)
        for (j in i..<size) {
            val swapped = this.toMutableList()
            swapped[i] = this[j]
            swapped[j] = this[i]

            val permutations = swapped.permutations(level + 1)
            result.addAll(permutations)
        }

    return result
}

fun Any?.println() = println(this)

fun Double.round(decimals: Int): Double {
    val scalar = (10.0).pow(decimals)
    return round(this * scalar) / scalar
}