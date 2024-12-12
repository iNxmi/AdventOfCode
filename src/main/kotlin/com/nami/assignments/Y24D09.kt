package com.nami.assignments

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import org.joml.Vector2i
import kotlin.math.pow

class Y24D09 : Assignment<String>(2024, 9) {

    override fun getRawTestInput(): TestInput {
        return TestInputSimplex("2333133121414131402")
    }

    override fun getProcessedInput(raw: String): String {
        return raw
    }

    override fun solveA(input: String): Int {
        return -1
    }


    override fun solveB(input: String): Int {
        return -1
    }

}