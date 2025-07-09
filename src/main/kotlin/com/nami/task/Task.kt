package com.nami.task

import com.nami.task.input.Input
import com.nami.task.solutions.y15.*
import com.nami.task.solutions.y16.Y16D01
import com.nami.task.solutions.y23.Y23D01
import com.nami.task.solutions.y23.Y23D02
import com.nami.task.solutions.y23.Y23D03
import com.nami.task.solutions.y23.Y23D04
import com.nami.task.solutions.y24.*
import net.steppschuh.markdowngenerator.table.Table

abstract class Task<InputClass : Any>(
    val year: Int,
    val day: Int,
    val id: Int = getID(year, day)
) {

    companion object {
        fun getID(year: Int, day: Int): Int = year * 100 + day

        fun getAll(): Set<Task<*>> = setOf(
            Y15D01(),
            Y15D02(),
            Y15D03(),
            Y15D04(),
            Y15D05(),
            Y15D06(),
            Y15D07(),
            Y15D08(),
            Y15D09(),
            Y15D10(),
            Y15D11(),
            Y15D12(),
            Y15D13(),
            Y15D14(),
            Y15D15(),
            Y15D16(),
            Y15D18(),

            Y16D01(),

            Y23D01(),
            Y23D02(),
            Y23D03(),
            Y23D04(),

            Y24D01(),
            Y24D02(),
            Y24D03(),
            Y24D04(),
            Y24D05(),
            Y24D06(),
            Y24D07(),
            Y24D08(),
            Y24D11(),
            Y24D15()
        )
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

        val a = getPartA().getResult(processed)
        val b = getPartB().getResult(processed)

        return Pair(a, b)
    }

    fun getResultsTest() = getResultsTest(getRawInputTest()!!)
    fun getResultsTest(input: Input): Pair<Result, Result> {
        val processedA = getProcessedInput(input.getRawTestInputA())
        val processedB = getProcessedInput(input.getRawTestInputB())

        val aTest = getPartA().getResultTest(processedA)
        val bTest = getPartB().getResultTest(processedB)

        return Pair(aTest, bTest)
    }

    fun printResult() = printResult(getRawInput())
    fun printResult(input: String) {
        val results = getResults(input)

        val builder = Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT)
            .addRow("Task", "Result", "Time (s)")
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
            getPartA().getVerification(processed, expected.first),
            getPartB().getVerification(processed, expected.second)
        )
    }

    fun printVerification() = printVerification(getRawInput())
    fun printVerification(input: String) {
        val verifications = getVerifications(input)

        val a = verifications.first
        val b = verifications.second

        val table = Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT)
            .addRow("Task", "Status", "Expected", "Actual", "Time (s)")
            .addRow("A", a.status, a.expected, a.result.value, ("%.2fs").format(a.result.timeInSeconds))
            .addRow("B", b.status, b.expected, b.result.value, ("%.2fs").format(b.result.timeInSeconds))
            .build()

        println(table.toString())
    }

}