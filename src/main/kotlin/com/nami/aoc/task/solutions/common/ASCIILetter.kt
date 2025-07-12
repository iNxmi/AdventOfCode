package com.nami.aoc.task.solutions.common

import kotlinx.serialization.json.Json
import org.joml.Vector2i
import java.nio.file.Files
import java.nio.file.Paths

class ASCIILetter {
    companion object {
        private val PATH = Paths.get("src/main/resources/alphabet.json")

        val LETTER_SIZE = Vector2i(5, 6)
        val MAP_ORIGINAL = Json.decodeFromString<Map<String, String>>(Files.readString(PATH))
        val MAP_REVERSED = MAP_ORIGINAL.entries.associateBy({ it.value }) { it.key }
    }
}