package com.nami

interface Assignment<T> {

    fun year(): Int
    fun day(): Int

    fun getInput(): String {
        return Input.get(year(), day())
    }

    fun getProcessedInput(): T

    fun solveA(): Int
    fun solveB(): Int

}