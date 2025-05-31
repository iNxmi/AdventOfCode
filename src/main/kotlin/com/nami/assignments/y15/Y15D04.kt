package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInputSimplex
import java.security.MessageDigest

class Y15D04 : Assignment<String>(2015, 4) {

    override fun getRawTestInput() = TestInputSimplex("pqrstuv")

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

            if (hash.startsWith(prefix)) break

            i++
        }

        return i
    }

    override fun solveA(input: String) = crack(input, "00000")
    override fun solveB(input: String) = crack(input, "000000")

}

fun main() = println(Y15D04().solve())