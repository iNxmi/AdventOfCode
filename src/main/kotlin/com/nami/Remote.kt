package com.nami

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.io.path.exists

//TODO THIS CLASS NEEDS CLEANUP ASAP

class Remote {

    enum class Type {
        INPUT,
        SOLUTION_A,
        SOLUTION_B
    }

    companion object {
        private val YEAR_RANGE = 2015..2024
        private val DAY_RANGE = 1..25

        private val TOKEN_ORIGINAL = dotenv().get("SESSION")
        private val TOKEN_RAW = TOKEN_ORIGINAL.toByteArray()
        private val TOKEN_HASH = MessageDigest.getInstance("SHA-256").digest(TOKEN_RAW)
        private val TOKEN_STRING = TOKEN_HASH.joinToString { ("%02x").format(it) }.replace(" ", "").replace(",", "")

        private val PATH = Paths.get("cache.json")
        private val MAP: MutableMap<String, MutableMap<Type, MutableMap<Int, String>>> = load()

        private fun load(): MutableMap<String, MutableMap<Type, MutableMap<Int, String>>> {
            if (!PATH.exists())
                return mutableMapOf()

            val string = Files.readString(PATH)
            return Json.decodeFromString<MutableMap<String, MutableMap<Type, MutableMap<Int, String>>>>(string)
        }

        private fun write() {
            val string = Json { prettyPrint = true }.encodeToString(MAP)
            Files.writeString(PATH, string)
        }

        fun getInput(year: Int, day: Int): String {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }
            val id = Assignment.getID(year, day)

            if (!MAP.containsKey(TOKEN_STRING))
                MAP[TOKEN_STRING] = mutableMapOf()

            val entriesToken = MAP[TOKEN_STRING]!!
            if (!entriesToken.containsKey(Type.INPUT))
                entriesToken[Type.INPUT] = mutableMapOf()

            val entriesInput = entriesToken[Type.INPUT]!!
            if (!entriesInput.containsKey(id)) {
                entriesInput[id] = fetchInput(year, day)
                write()
            }

            return entriesInput[id]!!
        }

        fun getSolutionA(year: Int, day: Int): String? {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }
            val id = Assignment.getID(year, day)

            if (!MAP.containsKey(TOKEN_STRING))
                MAP[TOKEN_STRING] = mutableMapOf()

            val entriesToken = MAP[TOKEN_STRING]!!
            if (!entriesToken.containsKey(Type.SOLUTION_A))
                entriesToken[Type.SOLUTION_A] = mutableMapOf()

            val entriesInput = entriesToken[Type.SOLUTION_A]!!
            if (!entriesInput.containsKey(id)) {
                val solutions = fetchSolutions(year, day)
                if (solutions.first == null)
                    return null

                entriesInput[id] = solutions.first!!
                write()
            }

            return entriesInput[id]!!
        }

        fun getSolutionB(year: Int, day: Int): String? {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }
            val id = Assignment.getID(year, day)

            if (!MAP.containsKey(TOKEN_STRING))
                MAP[TOKEN_STRING] = mutableMapOf()

            val entriesToken = MAP[TOKEN_STRING]!!
            if (!entriesToken.containsKey(Type.SOLUTION_B))
                entriesToken[Type.SOLUTION_B] = mutableMapOf()

            val entriesInput = entriesToken[Type.SOLUTION_B]!!
            if (!entriesInput.containsKey(id)) {
                val solutions = fetchSolutions(year, day)
                if (solutions.second == null)
                    return null

                entriesInput[id] = solutions.second!!
                write()
            }

            return entriesInput[id]!!
        }

        private fun fetchInput(year: Int, day: Int): String {
            val url = "https://adventofcode.com/$year/day/$day/input"
            println("Fetching Input ${year}_${day} -> $url")

            val doc = Jsoup.connect(url).cookie("session", TOKEN_ORIGINAL).get()
            val string = doc.connection().execute().body().trim()

            return string
        }

        private fun fetchSolutions(year: Int, day: Int): Pair<String?, String?> {
            val url = "https://adventofcode.com/$year/day/$day"
            println("Fetching Solutions ${year}_${day} -> $url")

            val doc = Jsoup.connect(url).cookie("session", TOKEN_ORIGINAL).get()
            val string = doc.connection().execute().body().trim()

            val results = ("<p>Your puzzle answer was <code>.*<\\/code>\\.<\\/p>").toRegex().findAll(string)
                .map {
                    it.value
                        .replace("<p>Your puzzle answer was <code>", "")
                        .replace("</code>.</p>", "")
                        .trim()
                }.toList()

            return Pair(results.getOrNull(0), results.getOrNull(1))
        }

    }
}