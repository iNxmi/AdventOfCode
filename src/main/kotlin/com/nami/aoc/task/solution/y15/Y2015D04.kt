package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import java.security.MessageDigest

class Y2015D04 : Task<String>(2015, 4) {

    override fun getProcessedInput(raw: String) = raw

    @OptIn(ExperimentalStdlibApi::class)
    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.toHexString()
    }

    private fun crack(secret: String, prefix: String): Int {
        var i = 0
        while (true) {
            val string = "$secret$i"
            val hash = md5(string)

            if (hash.startsWith(prefix))
                break

            i++
        }

        return i
    }

    override fun getPartA() = object : Part<String>(
        this, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: String) = crack(input, "00000")
    }

    override fun getPartB() = object : Part<String>(
        this, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: String) = crack(input, "000000")
    }

}