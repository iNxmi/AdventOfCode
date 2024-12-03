package com.nami

import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.text.Charsets.UTF_8

class Compress {

    companion object {

        fun compress(content: String): String {
            val bos = ByteArrayOutputStream()
            GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
            return String(bos.toByteArray())
        }

        fun decompress(content: String): String =
            GZIPInputStream(content.toByteArray().inputStream()).bufferedReader(UTF_8).use { it.readText() }

    }


}