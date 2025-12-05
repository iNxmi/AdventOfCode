package com.nami.aoc.task.solution

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Template : Task<String>(0, 0) {

    override fun getProcessedInput(raw: String) = raw

    override fun getPartA() = object : Part.A<String>(this) {
        override fun solve(input: String) = null
    }

    override fun getPartB() = object : Part.B<String>(this) {
        override fun solve(input: String) = null
    }

}

fun main() = Template().getTestVerifications().print()
//fun main() = Template().getResults().print()
//fun main() = Template().getVerifications().print()