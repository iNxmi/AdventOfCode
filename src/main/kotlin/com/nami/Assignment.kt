package com.nami

abstract class Assignment {

    abstract fun year(): Int
    abstract fun day(): Int

    private fun input(): String {
        return Input.get(year(), day())
    }

    fun solveA(): Int {
        return solveA(input())
    }

    fun solveB(): Int {
        return solveB(input())
    }

    protected abstract fun solveA(input: String): Int
    protected abstract fun solveB(input: String): Int


}