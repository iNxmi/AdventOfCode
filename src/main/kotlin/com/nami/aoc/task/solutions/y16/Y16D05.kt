package com.nami.aoc.task.solutions.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex
import java.security.MessageDigest

class Y16D05 : Task<String>(2016, 5) {

    override fun getRawInputTest() = InputSimplex("abc")

    override fun getProcessedInput(raw: String) = raw

    @OptIn(ExperimentalStdlibApi::class)
    override fun getPartA() = object : Part<String> {
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
    override fun getPartB() = object : Part<String> {
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

fun main() = Y16D05().printResult()
//fun main() = Y16D05().printVerification()
