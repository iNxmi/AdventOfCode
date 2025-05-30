package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInput
import com.nami.test.TestInputSimplex
import kotlinx.serialization.json.*

class Y15D12 : Assignment<Y15D12.Input, Int>(2015, 12) {

    override fun getRawTestInput(): TestInput = TestInputSimplex(
        """
        {
           "a": [1, 2, 3],
           "b": [1, {"c": "red", "d": 2}, 3],
           "e": {"f": "red", "g": [1, 2, 3, 4], "h": 5},
           "i": [1, "red", 5],
           "z": 10
        }
        """.trimIndent()
    )

    data class Input(val raw: String, val json: JsonObject)

    override fun getProcessedInput(raw: String): Input {
        val json = Json.decodeFromString<JsonObject>(raw)
        return Input(raw, json)
    }

    private val regex = ("(-*(\\d+))").toRegex()
    private fun sum(string: String): Int = regex.findAll(string).sumOf { it.value.toInt() }

    override fun solveA(input: Input): Int = sum(input.raw)

    override fun solveATest(input: Input): Int = solveA(input)

    private fun filterArray(json: JsonArray, predicate: String): JsonArray? {
        println("arr; $json")

        val result = buildJsonArray {
            for (value in json) {
                if (value is JsonObject) {
                    val filtered = filterObject(value, predicate)
                    if (filtered != null) {
                        add(filtered)
                        println("arr,obj; $value")
                    }
                }

                if (value is JsonArray) {
                    val filtered = filterArray(value, predicate)
                    if (filtered != null) {
                        add(filtered)
                        println("arr,arr; $value")
                    }
                }

                if (value is JsonPrimitive) {
                    add(value)
                    println("arr,pri; $value")
                }
            }
        }

        return result
    }

    private fun isJsonObjectValid(json: JsonObject, predicate: String): Boolean =
        !(json.values.any { (it is JsonPrimitive) && it.isString && (it.content == predicate) })

    private fun filterObject(json: JsonObject, predicate: String): JsonObject? {
        println("obj; $json")

        if (!isJsonObjectValid(json, predicate))
            return null

        val result = buildJsonObject {
            for ((key, value) in json) {
                if (value is JsonObject) {
                    val filtered = filterObject(value, predicate)
                    if (filtered != null) {
                        put(key, filtered)
                        println("obj,obj; $key=$value")
                    }
                }

                if (value is JsonArray) {
                    val filtered = filterArray(value, predicate)
                    if (filtered != null) {
                        put(key, filtered)
                        println("obj,arr; $key=$value")
                    }
                }

                if (value is JsonPrimitive) {
                    put(key, value)
                    println("obj,pri; $key=$value")
                }
            }
        }

        return result
    }

    override fun solveB(input: Input): Int {
        val filtered = filterObject(input.json, "red")
        return sum(filtered.toString())
    }

    override fun solveBTest(input: Input): Int = solveB(input)

}

fun main() = println(Y15D12().solve())
