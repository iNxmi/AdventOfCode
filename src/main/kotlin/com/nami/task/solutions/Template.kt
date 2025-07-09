package com.nami.task.solutions

import com.nami.task.Part
import com.nami.task.Task

class Template : Task<List<String>>(0, 0) {

    override fun getRawInputTest() = null

    override fun getProcessedInput(raw: String) = raw.lines()

    override fun getPartA() = object : Part<List<String>> {
        override fun solve(input: List<String>) = null
        override fun test(input: List<String>) = solve(input)
        override fun bonus() = null
        override fun comment() = null
    }

    override fun getPartB() = object : Part<List<String>> {
        override fun solve(input: List<String>) = null
        override fun test(input: List<String>) = solve(input)
        override fun bonus() = null
        override fun comment() = null
    }

}

fun main() = Template().printResult()
