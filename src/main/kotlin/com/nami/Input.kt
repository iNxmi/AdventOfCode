package com.nami

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

class Input {

    companion object {
        private const val START_YEAR = 2015
        private const val LAST_YEAR = 2024

        private val TOKEN = dotenv().get("SESSION")
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
            if (!(START_YEAR..LAST_YEAR).contains(year))
                throw IllegalArgumentException("Year $year is not in range ($START_YEAR .. $LAST_YEAR)")

            //Check if day is between 1 and 25
            if (!(1..25).contains(day))
                throw IllegalArgumentException("Day $day is not in range (1 .. 25)")

            val id = Utils.getID(year, day)

            if (!cache.containsKey(TOKEN))
                cache[TOKEN] = mutableMapOf()

            if (!cache[TOKEN]!!.containsKey(id)) {
                val input = fetch(year, day)
                cache[TOKEN]!![id] = input

                val jsonString = Json { prettyPrint = true }.encodeToString(cache)
                Files.writeString(CACHE_PATH, jsonString)
            }

            return cache[TOKEN]!![id]!!
        }

        private fun fetch(year: Int, day: Int): String {
            val url = "https://adventofcode.com/$year/day/$day/input"

            val doc = Jsoup.connect(url).cookie("session", TOKEN).get()
            val string = doc.connection().execute().body().trim()

            return string
        }

    }

}