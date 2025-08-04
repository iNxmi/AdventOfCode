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
    fun getSolutions(): Map<Part.Suffix, String?> = Remote.getSolutions(year, day)
    abstract fun getProcessedInput(raw: String): InputClass

    abstract fun getPartA(): Part<InputClass>
    abstract fun getPartB(): Part<InputClass>

    data class Test(val input: String, val value: String)

    fun getTestCases(suffix: Part.Suffix): Set<Test> {
        val path = Paths.get("src/main/resources/tests/${year}_${("%02d").format(day)}_${suffix.string}.json")
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

    fun getTestVerifications() = getTestVerifications(Part.Suffix.A, Part.Suffix.B)
    fun getTestVerifications(vararg suffixes: Part.Suffix) = suffixes.flatMap { getTestVerification(it) }.toSet()
    fun getTestVerification(suffix: Part.Suffix): Set<Verification> {
        val part = when (suffix) {
            Part.Suffix.A -> getPartA()
            Part.Suffix.B -> getPartB()
        }

        val tests = getTestCases(suffix)
        return tests.map { test ->
            val processed = getProcessedInput(test.input)
            val result = part.getResult(processed, true)
            Verification(result, test.value)
        }.toSet()
    }

    fun getResults(input: String = getRawInput()) = getResults(setOf(Part.Suffix.A, Part.Suffix.B), input)
    fun getResults(suffixes: Collection<Part.Suffix>, input: String = getRawInput()) =
        suffixes.map { getResult(it, input) }.toSet()

    fun getResult(suffix: Part.Suffix, input: String = getRawInput()): Result {
        val part = when (suffix) {
            Part.Suffix.A -> getPartA()
            Part.Suffix.B -> getPartB()
        }

        val processed = getProcessedInput(input)
        return part.getResult(processed)
    }

    fun getVerifications(input: String = getRawInput()) = getVerifications(setOf(Part.Suffix.A, Part.Suffix.B), input)
    fun getVerifications(suffixes: Collection<Part.Suffix>, input: String = getRawInput()) =
        suffixes.map { getVerification(it, input)!! }.toSet()

    fun getVerification(suffix: Part.Suffix, input: String = getRawInput()) = getResult(suffix, input).getVerification()

}