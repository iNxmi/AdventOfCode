package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import java.security.MessageDigest

class Y2016D05 : Task<String>(2016, 5) {

    override fun getProcessedInput(raw: String) = raw

    @OptIn(ExperimentalStdlibApi::class)
    override fun getPartA() = object : Part<String>(
        year, day, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: String): Any {
            val password = mutableListOf<String>()
            var i = 0
            while (password.size < 8) {
                val bytes = ("$input$i").toByteArray()
                val hash = MessageDigest.getInstance("MD5").digest(bytes).toHexString()

                if (hash.startsWith("00000"))
                    password.add(hash.elementAt(5).toString())

                i++
            }

            return password.joinToString("")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun getPartB() = object : Part<String>(
        year,day,Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: String): Any {
            val password = mutableMapOf<Int, String>()
            var i = 0
            while (password.size < 8) {
                val bytes = ("$input${i++}").toByteArray()
                val hash = MessageDigest.getInstance("MD5").digest(bytes).toHexString()

                if (hash.startsWith("00000")) {
                    val position = hash.elementAt(5)
                    if (!("01234567").toCharArray().contains(position))
                        continue

                    if (password.containsKey(position.digitToInt()))
                        continue

                    val char = hash.elementAt(6).toString()
                    password[position.digitToInt()] = char
                }
            }

            return password.toSortedMap().map { it.value }.joinToString("")
        }
    }

}
