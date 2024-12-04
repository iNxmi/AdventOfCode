package com.nami.assignments

import com.nami.Assignment
import java.security.MessageDigest

class Y15D05 : Assignment<List<String>>(2015, 5) {

    override fun getProcessedInput(raw: String): List<String> {
        return raw.lines()
    }

    override fun solveA(input: List<String>): Int {
        var count = 0
        for (line in input) {
            var vowelCount = 0
            if (line.contains("a")) vowelCount++
            if (line.contains("e")) vowelCount++
            if (line.contains("i")) vowelCount++
            if (line.contains("o")) vowelCount++
            if (line.contains("u")) vowelCount++
            if (vowelCount < 3) continue

            var containsDouble = false
            var lastC = '0'
            for (c in line.toCharArray()) {
                if (c == lastC) {
                    containsDouble = true
                    break
                }

                lastC = c
            }
            if (!containsDouble) continue

            val naughtySequence = line.contains("ab") ||
                    line.contains("cd") ||
                    line.contains("pq") ||
                    line.contains("xy")
            if (naughtySequence) continue

            println(line)

            count++
        }

        return count
    }

    override fun solveB(input: List<String>): Int {
        return -1
    }

}