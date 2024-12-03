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
            //Check if year is between 2015 and 2023
            if (!(2015..2024).contains(year))
                throw IllegalArgumentException("Year $year is not in range (2015 .. 2024)")

            //Check if day is between 1 and 25
            if (!(1..25).contains(day))
                throw IllegalArgumentException("Day $day is not in range (1 .. 25)")

            val id = year * 100 + day

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