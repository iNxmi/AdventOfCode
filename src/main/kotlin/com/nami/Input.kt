package com.nami

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import io.github.cdimascio.dotenv.dotenv

class Input {

    companion object {
        private val dotenv = dotenv()

        fun get(year: Int, day: Int): String {
            val url = URI("https://adventofcode.com/$year/day/$day/input").toURL()

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Cookie", "session=${dotenv["SESSION"]}")

            if(connection.responseCode != 200)
                throw IllegalStateException("Error: ($url) -> ${connection.responseCode} : ${connection.responseMessage}")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            return reader.readText()
        }

        fun verify(year: Int, day: Int, level : Int, answer: Int): String {
            val url = URI("https://adventofcode.com/$year/day/$day/answer").toURL()

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Cookie", "session=${dotenv["SESSION"]}")
            connection.setRequestProperty("level", level.toString())
            connection.setRequestProperty("answer", answer.toString())

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            return reader.readText()
        }

    }

}