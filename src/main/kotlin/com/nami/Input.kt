package com.nami

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

class Input {

    companion object {
        private val dotenv = dotenv()
        private var key = dotenv.get("SESSION")

        private val cachePath = Paths.get("cache.json")
        private var sessions = mutableMapOf<String, MutableMap<Int, String>>()

        init {
            if (cachePath.exists()) {
                var jsonString = Files.readString(cachePath)
                sessions = Json.decodeFromString<MutableMap<String, MutableMap<Int, String>>>(jsonString)
            }
        }

        fun fetch(year: Int, day: Int): String {
            val id = year * 100 + day

            if (sessions.containsKey(key)) {
                val inputs = sessions[key]
                if (inputs!!.containsKey(id)) {
                    return inputs[id]!!
                }
            }

            val url = "https://adventofcode.com/$year/day/$day/input"

            val doc: Document = Jsoup.connect(url).cookie("session", key).get()
            val string = doc.connection().execute().body().trim()

            if (!sessions.containsKey(key))
                sessions[key] = mutableMapOf()

            sessions[key]!![id] = string
            val jsonString = Json { prettyPrint = true }.encodeToString(sessions)
            Files.writeString(cachePath, jsonString)

            return string
        }

    }

}