package com.nami.aoc.task

import com.nami.aoc.task.input.Input
import io.github.classgraph.ClassGraph
import net.steppschuh.markdowngenerator.table.Table

abstract class Task<InputClass : Any>(
    val year: Int,
    val day: Int
) {

    val uid: UID = UID(year, day, UID.Part.ROOT)

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

    fun getResultsTest() = getResultsTest(getRawInputTest()!!)
    fun getResultsTest(input: Input): Pair<Result, Result> {
        val processedA = getProcessedInput(input.getRawTestInputA())
        val processedB = getProcessedInput(input.getRawTestInputB())

        val aTest = getPartA().getResultTest(UID(year, day, UID.Part.A_TEST), processedA)
        val bTest = getPartB().getResultTest(UID(year, day, UID.Part.B_TEST), processedB)

        return Pair(aTest, bTest)
    }

    fun printResult() = printResult(getRawInput())
    fun printResult(input: String) {
        val results = getResults(input)

        val builder = Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT)
            .addRow("$year/$day", "Result", "Time (s)")
            .addRow("A", results.first.value, ("%.2fs").format(results.first.timeInSeconds))
            .addRow("B", results.second.value, ("%.2fs").format(results.second.timeInSeconds))

        val rawInputTest = getRawInputTest()
        val hasTestInput = rawInputTest != null
        if (hasTestInput) {
            val resultsTest = getResultsTest(rawInputTest)
            builder.addRow("A_TEST", resultsTest.first.value, ("%.2fs").format(resultsTest.first.timeInSeconds))
            builder.addRow("B_TEST", resultsTest.second.value, ("%.2fs").format(resultsTest.second.timeInSeconds))
        }

        println(builder.build().toString())
    }

    fun getVerifications() = getVerifications(getRawInput())
    fun getVerifications(input: String): Pair<Verification, Verification> {
        val processed = getProcessedInput(input)
        val expected = Remote.getSolutions(year, day)

        return Pair(
            getPartA().getVerification(UID(year, day, UID.Part.A), processed, expected.first),
            getPartB().getVerification(UID(year, day, UID.Part.B), processed, expected.second)
        )
    }

    fun printVerification() = printVerification(getRawInput())
    fun printVerification(input: String) {
        val verifications = getVerifications(input)

        val a = verifications.first
        val b = verifications.second

        val table = Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT)
            .addRow("$year/$day", "Status", "Expected", "Actual", "Time (s)")
            .addRow("A", a.status, a.expected, a.result.value, ("%.2fs").format(a.result.timeInSeconds))
            .addRow("B", b.status, b.expected, b.result.value, ("%.2fs").format(b.result.timeInSeconds))
            .build()

        println(table.toString())
    }

}