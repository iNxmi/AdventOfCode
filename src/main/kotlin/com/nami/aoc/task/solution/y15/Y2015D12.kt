package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlinx.serialization.json.*

class Y2015D12 : Task<Y2015D12.Input>(2015, 12) {

    data class Input(val raw: String, val json: JsonObject)

    override fun getProcessedInput(raw: String): Input {
        val json = Json.decodeFromString<JsonObject>(raw)
        return Input(raw, json)
    }

    private val regex = ("(-*(\\d+))").toRegex()
    private fun sum(string: String): Int = regex.findAll(string).sumOf { it.value.toInt() }

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

    override fun getPartA() = object : Part<Input>(
        year, day, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: Input) = sum(input.raw)
    }

    override fun getPartB() = object : Part<Input>(
        year, day, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: Input): Any {
            val filtered = filterObject(input.json, "red")
            return sum(filtered.toString())
        }
    }

}
