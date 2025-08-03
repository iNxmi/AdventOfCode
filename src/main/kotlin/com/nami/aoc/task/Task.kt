package com.nami.aoc.task

import io.github.classgraph.ClassGraph
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Paths

abstract class Task<InputClass : Any>(
    val year: Int,
    val day: Int
) {

    companion object {
        const val PATH = "com.nami.aoc.task.solution"
        fun getAll(): Set<Task<*>> {
            val classes = ClassGraph()
                .acceptPackages(PATH)
                .scan()
                .getSubclasses(Task::class.java.name)
                .filter { it.packageName.startsWith("$PATH.y") }

            val tasks = classes.map { it.loadClass().getDeclaredConstructor().newInstance() as Task<*> }.toSet()
            return tasks
        }
    }

    protected val log = KotlinLogging.logger("${year}_$day")

    fun getRawInput(): String = Remote.getInput(year, day)
    fun getSolutions(): Map<Part.Type, String?> = Remote.getSolutions(year, day)
    abstract fun getProcessedInput(raw: String): InputClass

    abstract fun getPartA(): Part<InputClass>
    abstract fun getPartB(): Part<InputClass>

    data class Test(val input: String, val value: String)

    fun getTestCases(type: Part.Type): Set<Test> {
        val path = Paths.get("src/main/resources/tests/${year}_${("%02d").format(day)}_${type.string}.json")
        val json: Map<String, List<String>> = try {
            val string = Files.readString(path)
            Json.decodeFromString<Map<String, List<String>>>(string)
        } catch (_: Exception) {
            return emptySet()
        }

        val result = mutableSetOf<Test>()
        json.forEach { value, inputs ->
            inputs.forEach { input ->
                result.add(Test(input, value))
            }
        }
        return result
    }

    fun getTestVerifications() = getTestVerifications(Part.Type.A, Part.Type.B)
    fun getTestVerifications(vararg types: Part.Type) = types.flatMap { getTestVerification(it) }.toSet()
    fun getTestVerification(type: Part.Type): Set<Verification> {
        val part = when (type) {
            Part.Type.A -> getPartA()
            Part.Type.B -> getPartB()
        }

        val tests = getTestCases(type)
        return tests.map { test ->
            val processed = getProcessedInput(test.input)
            val result = part.getResult(processed, true)
            Verification(result, test.value)
        }.toSet()
    }

    fun getResults(input: String = getRawInput()) = getResults(setOf(Part.Type.A, Part.Type.B), input)
    fun getResults(types: Collection<Part.Type>, input: String = getRawInput()) =
        types.map { getResult(it, input) }.toSet()

    fun getResult(type: Part.Type, input: String = getRawInput()): Result {
        val part = when (type) {
            Part.Type.A -> getPartA()
            Part.Type.B -> getPartB()
        }

        val processed = getProcessedInput(input)
        return part.getResult(processed)
    }

    fun getVerifications(input: String = getRawInput()) = getVerifications(setOf(Part.Type.A, Part.Type.B), input)
    fun getVerifications(types: Collection<Part.Type>, input: String = getRawInput()) =
        types.map { getVerification(it, input)!! }.toSet()

    fun getVerification(type: Part.Type, input: String = getRawInput()) = getResult(type, input).getVerification()

}