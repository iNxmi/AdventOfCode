package com.nami.task

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest

//TODO THIS CLASS NEEDS CLEANUP ASAP

class Remote {

    companion object {
        private val YEAR_RANGE = 2015..2024
        private val DAY_RANGE = 1..25

        private val TOKEN_ORIGINAL = dotenv().get("SESSION")
        private val TOKEN_HASH = MessageDigest.getInstance("SHA-256").digest(TOKEN_ORIGINAL.toByteArray())
        private val TOKEN_STRING = TOKEN_HASH.joinToString { ("%02x").format(it) }.replace(" ", "").replace(",", "")

        private val PATH = Paths.get("cache.json")
        private val MAP: MutableMap<String, MutableMap<Int, String>> = load()

        private fun load() = try {
            val string = Files.readString(PATH)
            Json.decodeFromString<MutableMap<String, MutableMap<Int, String>>>(string)
        } catch (_: Exception) {
            mutableMapOf()
        }

        private fun write() {
            val string = Json { prettyPrint = true }.encodeToString(MAP)
            Files.writeString(PATH, string)
        }

        fun getInput(year: Int, day: Int): String {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }

            if (!MAP.containsKey(TOKEN_STRING))
                MAP[TOKEN_STRING] = mutableMapOf()

            val id = UID(year, day, UID.Part.ROOT).id

            val entries = MAP[TOKEN_STRING]!!
            if (!entries.containsKey(id)) {
                entries[id] = fetchInput(year, day)
                write()
            }

            return entries[id]!!
        }

        fun getSolutions(year: Int, day: Int): Pair<String?, String?> {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }


            if (!MAP.containsKey(TOKEN_STRING))
                MAP[TOKEN_STRING] = mutableMapOf()

            val entries = MAP[TOKEN_STRING]!!

            val idA = UID(year, day, UID.Part.A).id
            val idB = UID(year, day, UID.Part.B).id

            val solutions = if (!entries.containsKey(idA) || !entries.containsKey(idB)) {
                fetchSolutions(year, day)
            } else {
                null
            }

            var write = false

            if (!entries.containsKey(idA)) {
                val solution = solutions!!.first
                if (solution != null) {
                    entries[idA] = solution
                    write = true
                }
            }

            if (!entries.containsKey(idB)) {
                val solution = solutions!!.second
                if (solution != null) {
                    entries[idB] = solution
                    write = true
                }
            }

            if (write)
                write()

            return Pair(entries[idA], entries[idB])
        }

        private fun fetchInput(year: Int, day: Int): String {
            val url = "https://adventofcode.com/$year/day/$day/input"
            println("Fetching ${year}_${day} Input -> $url")

            val doc = fetchDocument(url, TOKEN_ORIGINAL)
            val string = doc.connection().execute().body().trim()

            return string
        }

        private fun fetchSolutions(year: Int, day: Int): Pair<String?, String?> {
            val url = "https://adventofcode.com/$year/day/$day"
            println("Fetching ${year}_${day} Solutions -> $url")

            val doc = fetchDocument(url, TOKEN_ORIGINAL)
            val string = doc.connection().execute().body().trim()

            val results = Regex("<p>Your puzzle answer was <code>.*<\\/code>\\.<\\/p>").findAll(string).map {
                    it.value
                        .replace("<p>Your puzzle answer was <code>", "")
                        .replace("</code>.</p>", "")
                        .trim()
                }.toList()

            return Pair(results.getOrNull(0), results.getOrNull(1))
        }

        private fun fetchDocument(url: String, token: String) = try {
            Jsoup.connect(url)
                .cookie("session", token)
                .get()
        } catch (e: HttpStatusException) {
            throw HttpStatusException(
                "HTTP error fetching URL -> Is your SESSION invalid?",
                e.statusCode,
                e.url
            )
        }

    }
}