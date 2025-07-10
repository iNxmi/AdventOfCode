package com.nami.task.solutions.y16

import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputDuplex

class Y16D04 : Task<List<Y16D04.Room>>(2016, 4) {

    data class Room(val name: String, val id: Int, val checksum: String) {

        private fun checksum(): String {
            val sanitized = Regex("\\w*").findAll(name).map { it.groupValues[0] }.toList().joinToString("")
            val chars = sanitized.toCharArray()
            val count = chars.toSet().map { char -> Pair(char, chars.count { it == char }) }
            val sorted = count.sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { it.first })
            val checksum = sorted.subList(0, 5).joinToString("") { it.first.toString() }
            return checksum
        }

        fun isValid() = checksum() == checksum

        private val BASE = ('a').code
        private val ALPHABET_SIZE = 26
        private fun decrypt(): String {
            val shifted = name
                .replace("-", " ")
                .toCharArray()
                .joinToString("") { char ->
                    if (!char.isLetter())
                        char.toString()

                    val offset = char.code - BASE
                    (BASE + ((id + offset) % ALPHABET_SIZE)).toChar().toString()
                }
            return shifted
        }

        fun getDecrypted() = Room(decrypt(), id, checksum)

    }

    override fun getRawInputTest() = InputDuplex(
        """
        aaaaa-bbb-z-y-x-123[abxyz]
        a-b-c-d-e-f-g-h-987[abcde]
        not-a-real-room-404[oarel]
        totally-real-room-200[decoy]
        """.trimIndent(),
        "qzmt-zixmtkozy-ivhz-343[aaaaa]"
    )

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val name = Regex("(.*?)-\\d").find(line)!!.groupValues[1]
        val id = Regex("\\d+").find(line)!!.groupValues[0].toInt()
        val checksum = Regex("\\[(\\w+)\\]").find(line)!!.groupValues[1]
        Room(name, id, checksum)
    }

    override fun getPartA() = object : Part<List<Room>> {
        override fun solve(input: List<Room>) = input
            .filter { it.isValid() }
            .sumOf { it.id }
    }

    override fun getPartB() = object : Part<List<Room>> {
        override fun solve(input: List<Room>) = input
            .map { it.getDecrypted() }
            .find { it.name.contains("north") }?.id
    }

}

//fun main() = Y16D04().printResult()
fun main() = Y16D04().printVerification()
