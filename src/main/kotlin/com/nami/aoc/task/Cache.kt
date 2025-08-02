package com.nami.aoc.task

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.path.absolute
import kotlin.io.path.createParentDirectories

class Cache(
    val path: Path
) : LinkedHashMap<Int, String>() {

    companion object {
        val JSON = Json { prettyPrint = true }
    }

    init {
        try {
            val string = Files.readString(path)
            val result = Json.decodeFromString<MutableMap<Int, String>>(string)
            putAll(result)
        } catch (_: Exception) {
        }
    }

    fun write() {
        path.createParentDirectories()
        Files.writeString(path, JSON.encodeToString(this.toMap()))
    }

}