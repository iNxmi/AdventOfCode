package com.nami

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
