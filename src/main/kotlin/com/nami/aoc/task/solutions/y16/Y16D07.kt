package com.nami.aoc.task.solutions.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputDuplex

class Y16D07 : Task<List<Y16D07.IPv7>>(2016, 7) {

    data class IPv7(val supernet: List<String>, val hypernet: List<String>) {

        private val abba = Regex("(.)(?!\\1)(.)\\2\\1")
        private fun isABBA(string: String) = abba.containsMatchIn(string)
        fun canTLS() = supernet.any { isABBA(it) } && !hypernet.any { isABBA(it) }

        private val aba = Regex("(.)(?!\\1)(.)\\1")
        private fun isABA(string: String) = aba.containsMatchIn(string)
        private fun isBAB(string: String, charB: Char, charA: Char) =
            Regex("$charB$charA$charB").containsMatchIn(string)

        fun canSSL(): Boolean {
            val allABA = mutableSetOf<String>()
            supernet.forEach { supernet ->
                for (i in 0 until supernet.length - (3 - 1)) {
                    val substring = supernet.substring(i, i + 3)
                    if (isABA(substring))
                        allABA.add(substring)
                }
            }

            if (allABA.isEmpty())
                return false

            hypernet.forEach { hypernet ->
                allABA.forEach { aba ->
                    if (isBAB(hypernet, aba[1], aba[0]))
                        return true
                }
            }

            return false
        }

    }

    override fun getRawInputTest() = InputDuplex(
        """
        abba[mnop]qrst
        abcd[bddb]xyyx
        aaaa[qwer]tyui
        ioxxoj[asdfgh]zxcvbn
        """.trimIndent(),
        """
        aba[bab]xyz
        xyx[xyx]xyx
        aaa[kek]eke
        zazbz[bzb]cdb
        """.trimIndent()
    )

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val split = Regex("[\\[\\]]").split(line)
        val supernet = split.filterIndexed { index, _ -> index % 2 == 0 }
        val hypernet = split.filterIndexed { index, _ -> index % 2 == 1 }

        IPv7(supernet, hypernet)
    }

    override fun getPartA() = object : Part<List<IPv7>> {
        override fun solve(input: List<IPv7>) = input.count { it.canTLS() }
    }

    override fun getPartB() = object : Part<List<IPv7>> {
        override fun solve(input: List<IPv7>) = input.count { it.canSSL() }
    }

}

fun main() = Y16D07().printResults()
//fun main() = Y16D07().printVerification()
