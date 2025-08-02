package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2016D04 : Task<List<Y2016D04.Room>>(2016, 4) {

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

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val name = Regex("(.*?)-\\d").find(line)!!.groupValues[1]
        val id = Regex("\\d+").find(line)!!.groupValues[0].toInt()
        val checksum = Regex("\\[(\\w+)\\]").find(line)!!.groupValues[1]
        Room(name, id, checksum)
    }

    override fun getPartA() = object : Part<List<Room>>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Room>) = input
            .filter { it.isValid() }
            .sumOf { it.id }
    }

    override fun getPartB() = object : Part<List<Room>>(
        this, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<Room>) = input
            .map { it.getDecrypted() }
            .find { it.name.contains("north") }?.id
    }

}
