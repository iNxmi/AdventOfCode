package com.nami.aoc.task

import com.nami.aoc.DAY_RANGE
import com.nami.aoc.YEAR_RANGE
import io.github.cdimascio.dotenv.dotenv
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.nio.file.Paths
import java.security.MessageDigest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.OffsetDateTime
import java.time.ZoneOffset

class Remote {

    companion object {
        private val TOKEN_ORIGINAL = dotenv().get("SESSION")
        private val TOKEN_HASH = MessageDigest.getInstance("SHA-256").digest(TOKEN_ORIGINAL.toByteArray())

        @OptIn(ExperimentalStdlibApi::class)
        private val TOKEN_STRING = TOKEN_HASH.toHexString()

        private val CACHE_INPUT = Cache(Paths.get("cache/$TOKEN_STRING/input.json"))
        private val CACHE_SOLUTION_A = Cache(Paths.get("cache/$TOKEN_STRING/solutions_a.json"))
        private val CACHE_SOLUTION_B = Cache(Paths.get("cache/$TOKEN_STRING/solutions_b.json"))

        //UTC-5
        private val AOC_TIME_ZONE = ZoneOffset.ofHours(-5)

        fun verify(year: Int, day: Int) {
            require(YEAR_RANGE.contains(year)) { "Year must be in '$YEAR_RANGE'" }

            val dayRange = DAY_RANGE[year]!!
            require(dayRange.contains(day)) { "Day must be in '$dayRange'" }

            val request = LocalDateTime.of(year, Month.DECEMBER, day, 0, 0).atOffset(AOC_TIME_ZONE)
            val current = LocalDateTime.now().atOffset(AOC_TIME_ZONE)
            require(request.isBefore(current)) { "Cannot request the future. request=$request current=$current" }
        }

        fun getInput(year: Int, day: Int): String {
            verify(year, day)

            val id = year * 100 + day
            if (!CACHE_INPUT.containsKey(id)) {
                CACHE_INPUT[id] = fetchInput(year, day)
                CACHE_INPUT.write()
            }

            return CACHE_INPUT[id]!!
        }

        fun getSolutions(year: Int, day: Int): Map<Part.Suffix, String?> {
            verify(year, day)

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

            return mutableMapOf<Part.Suffix, String?>().apply {
                put(Part.Suffix.A, CACHE_SOLUTION_A.getOrDefault(id, null))
                put(Part.Suffix.B, CACHE_SOLUTION_B.getOrDefault(id, null))
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