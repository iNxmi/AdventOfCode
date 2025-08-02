package com.nami.aoc.task

import io.github.cdimascio.dotenv.dotenv
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.nio.file.Paths
import java.security.MessageDigest

class Remote {

    companion object {
        private val YEAR_RANGE = (2015..2024)
        private val DAY_RANGE = (1..25)

        private val TOKEN_ORIGINAL = dotenv().get("SESSION")
        private val TOKEN_HASH = MessageDigest.getInstance("SHA-256").digest(TOKEN_ORIGINAL.toByteArray())
        private val TOKEN_STRING = TOKEN_HASH.joinToString("") { ("%02x").format(it) }

        private val CACHE_INPUT = Cache(Paths.get("cache/$TOKEN_STRING/input.json"))
        private val CACHE_SOLUTION_A = Cache(Paths.get("cache/$TOKEN_STRING/solutions_a.json"))
        private val CACHE_SOLUTION_B = Cache(Paths.get("cache/$TOKEN_STRING/solutions_b.json"))

        fun getInput(year: Int, day: Int): String {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }

            val id = year * 100 + day
            if (!CACHE_INPUT.containsKey(id)) {
                CACHE_INPUT[id] = fetchInput(year, day)
                CACHE_INPUT.write()
            }

            return CACHE_INPUT[id]!!
        }

        fun getSolutions(year: Int, day: Int): Map<Part.Type, String?> {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }
            require(DAY_RANGE.contains(day)) { "Day must be in '$DAY_RANGE'" }

            val id = year * 100 + day

            if (!CACHE_SOLUTION_A.contains(id) || !CACHE_SOLUTION_B.contains(id)) {
                val raw = fetchSolutions(year, day)

                if (!CACHE_SOLUTION_A.containsKey(id)) {
                    val solution = raw.first
                    if (solution != null) {
                        CACHE_SOLUTION_A[id] = solution
                        CACHE_SOLUTION_A.write()
                    }
                }

                if (!CACHE_SOLUTION_B.containsKey(id)) {
                    val solution = raw.second
                    if (solution != null) {
                        CACHE_SOLUTION_B[id] = solution
                        CACHE_SOLUTION_B.write()
                    }
                }
            }

            return mutableMapOf<Part.Type, String?>().apply {
                put(Part.Type.A, CACHE_SOLUTION_A.getOrDefault(id, null))
                put(Part.Type.B, CACHE_SOLUTION_B.getOrDefault(id, null))
            }
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