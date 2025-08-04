package com.nami.aoc.task.solution.common

import kotlinx.serialization.json.Json
import org.joml.Vector2i
import java.nio.file.Files
import java.nio.file.Paths

class ASCIILetter {
    companion object {
        private val PATH = Paths.get("src/main/resources/ascii_alphabet.json")

        val LETTER_SIZE = Vector2i(5, 6)
        private val MAP_ORIGINAL = Json.decodeFromString<Map<String, String?>>(Files.readString(PATH))
        private val MAP_REVERSED = MAP_ORIGINAL.entries.associateBy({ it.value }) { it.key }

        fun getASCIICharacter(character: Char): String {
            if (MAP_ORIGINAL.contains(character.toString()))
                throw NullPointerException("The character $character is not in the map")

            return MAP_ORIGINAL[character.toString()]!!
        }

        fun getCharacter(ascii: String): Char {
            if (!MAP_REVERSED.contains(ascii))
                throw NullPointerException("The ascii-combination is not in the map")

            return MAP_REVERSED[ascii]!![0]
        }

    }
}