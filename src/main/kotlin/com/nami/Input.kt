package com.nami

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

class Input {

    companion object {
        private const val YEAR_START = 2015
        private const val YEAR_END = 2024

        private val SESSION = dotenv().get("SESSION")
        private val CACHE_PATH = Paths.get("cache.json")

        private var cache = mutableMapOf<String, MutableMap<Int, String>>()

        init {
            if (CACHE_PATH.exists()) {
                val jsonString = Files.readString(CACHE_PATH)
                cache = Json.decodeFromString<MutableMap<String, MutableMap<Int, String>>>(jsonString)
            }
        }

        fun get(year: Int, day: Int): String {
            //Check if year is between START_YEAR and LAST_YEAR
            if (!(YEAR_START..YEAR_END).contains(year))
                throw IllegalArgumentException("Year $year is not in range ($YEAR_START .. $YEAR_END)")

            //Check if day is between 1 and 25
            if (!(1..25).contains(day))
                throw IllegalArgumentException("Day $day is not in range (1 .. 25)")

            val id = Utils.getID(year, day)

            if (!cache.containsKey(SESSION))
                cache[SESSION] = mutableMapOf()

            if (!cache[SESSION]!!.containsKey(id)) {
                val input = fetch(year, day)
                cache[SESSION]!![id] = input

                val jsonString = Json { prettyPrint = true }.encodeToString(cache)
                Files.writeString(CACHE_PATH, jsonString)
            }

            return cache[SESSION]!![id]!!
        }

        private fun fetch(year: Int, day: Int): String {
            val url = "https://adventofcode.com/$year/day/$day/input"
            println("Fetching ${year}_${day} -> $url")

            val doc = Jsoup.connect(url).cookie("session", SESSION).get()
            val string = doc.connection().execute().body().trim()

            return string
        }

    }

}