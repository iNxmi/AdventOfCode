package com.nami.task.solutions.y15

import com.nami.println
import com.nami.task.Task
import com.nami.task.test.TestInput
import com.nami.task.test.TestInputSimplex
import kotlinx.serialization.json.*

class Y15D12 : Task<Y15D12.Input>(2015, 12) {

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

    override fun solveA(input: Input): Any = sum(input.raw)

    private fun filterArray(json: JsonArray, predicate: String): JsonArray = buildJsonArray {
        json.forEach { value ->
            val filtered = when (value) {
                is JsonObject -> filterObject(value, predicate)
                is JsonArray -> filterArray(value, predicate)
                is JsonPrimitive -> value
            }
            add(filtered)
        }
    }

    private fun isJsonObjectValid(json: JsonObject, predicate: String): Boolean =
        !(json.values.any { (it is JsonPrimitive) && it.isString && (it.content == predicate) })

    private fun filterObject(json: JsonObject, predicate: String): JsonObject = buildJsonObject {
        if (!isJsonObjectValid(json, predicate))
            return@buildJsonObject

        json.forEach { (key, value) ->
            val filtered = when (value) {
                is JsonObject -> filterObject(value, predicate)
                is JsonArray -> filterArray(value, predicate)
                is JsonPrimitive -> value
            }
            put(key, filtered)
        }
    }

    override fun solveB(input: Input): Any {
        val filtered = filterObject(input.json, "red")
        return sum(filtered.toString())
    }

}

fun main() = Y15D12().solve().println()
