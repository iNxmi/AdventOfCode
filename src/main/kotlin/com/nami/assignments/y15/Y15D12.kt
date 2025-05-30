package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class Y15D12 : Assignment<Y15D12.Input, Int>(2015, 12) {

    override fun getRawTestInput(): TestInput = TestInputSimplex("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}")

    data class Input(val raw: String, val json: JsonObject)

    override fun getProcessedInput(raw: String): Input {
        val json = Json.decodeFromString<JsonObject>(raw)
        return Input(raw, json)
    }

    private val regex = ("(-*(\\d+))").toRegex()
    override fun solveA(input: Input): Int = regex.findAll(input.raw).sumOf { it.value.toInt() }

    override fun solveATest(input: Input): Int = solveA(input)

    override fun solveB(input: Input): Int {
        return -1
    }

    override fun solveBTest(input: Input): Int = solveB(input)

}

fun main() = println(Y15D12().solve())
