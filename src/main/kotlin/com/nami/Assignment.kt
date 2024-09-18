package com.nami

abstract class Assignment(
    private val year: Int,
    private val day: Int
) {

    protected abstract fun solve(input: String): Int

    fun solve(): Int {
        return solve(Input.fetch(year, day))
    }

}