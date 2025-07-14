package com.nami.aoc.task

import com.nami.aoc.task.input.Input
import io.github.classgraph.ClassGraph

abstract class Task<InputClass : Any>(
    val year: Int,
    val day: Int
) {

    val uid: UID = UID(year, day, UID.Part.ROOT)
    val url = "https://adventofcode.com/$year/day/$day"

    companion object {
        fun getAll(): Set<Task<*>> {
            val classes = ClassGraph()
                .acceptPackages("com.nami.aoc.task.solutions")
                .scan()
                .getSubclasses(Task::class.java.name)
                .filter { it.packageName.startsWith("com.nami.aoc.task.solutions.y") }

            val tasks = classes.map { it.loadClass().getDeclaredConstructor().newInstance() as Task<*> }.toSet()
            return tasks
        }
    }

    fun getRawInput(): String = Remote.getInput(year, day)
    abstract fun getRawInputTest(): Input?

    abstract fun getProcessedInput(raw: String): InputClass

    abstract fun getPartA(): Part<InputClass>
    abstract fun getPartB(): Part<InputClass>

    private fun print(content: Any?) = println(content)
    protected fun info(content: Any?) = print("Info: $content")
    protected fun debug(content: Any?) = print("Debug: $content")
    protected fun error(content: Any?) = print("Error: $content")
    protected fun critical(content: Any?) = print("Critical: $content")

    fun getResults() = getResults(getRawInput())
    fun getResults(input: String): Pair<Result, Result> {
        val processed = getProcessedInput(input)

        val a = getPartA().getResult(UID(year, day, UID.Part.A), processed)
        val b = getPartB().getResult(UID(year, day, UID.Part.B), processed)

        return Pair(a, b)
    }

    fun getResultsTest() = getResultsTest(getRawInputTest())
    fun getResultsTest(input: Input?): Pair<Result, Result>? {
        if (input == null)
            return null

        val processedA = getProcessedInput(input.getRawTestInputA())
        val processedB = getProcessedInput(input.getRawTestInputB())

        val aTest = getPartA().getResultTest(UID(year, day, UID.Part.A_TEST), processedA)
        val bTest = getPartB().getResultTest(UID(year, day, UID.Part.B_TEST), processedB)

        return Pair(aTest, bTest)
    }

    fun getVerifications() = getVerifications(getRawInput())
    fun getVerifications(input: String): Pair<Verification, Verification> {
        val processed = getProcessedInput(input)
        val expected = Remote.getSolutions(year, day)

        return Pair(
            getPartA().getVerification(this, UID(year, day, UID.Part.A), processed, expected.first),
            getPartB().getVerification(this, UID(year, day, UID.Part.B), processed, expected.second)
        )
    }

    fun printResults() = printResults(getResults())
    fun printResults(input: String) = printResults(getResults(input))
    fun printResults(results: Pair<Result, Result>) {
        val tests = getResultsTest()
        val output = Results(this, results.first, results.second, tests?.first, tests?.second).toString()
        println(output)
    }

    fun printVerifications() = printVerifications(getVerifications())
    fun printVerifications(input: String) = printVerifications(getVerifications(input))
    fun printVerifications(verifications: Pair<Verification, Verification>) {
        val output = Verifications(uid, verifications.first, verifications.second).toString()
        println(output)
    }

}