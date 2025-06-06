package com.nami.task

import com.nami.task.input.Input
import com.nami.task.solutions.y15.*
import com.nami.task.solutions.y16.Y16D01
import com.nami.task.solutions.y23.Y23D01
import com.nami.task.solutions.y23.Y23D02
import com.nami.task.solutions.y23.Y23D03
import com.nami.task.solutions.y23.Y23D04
import com.nami.task.solutions.y24.*

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

    abstract fun getRawInputTest(): Input?

    abstract fun getProcessedInput(raw: String): InputClass

    abstract fun getSubTaskA(): SubTask<InputClass>
    abstract fun getSubTaskB(): SubTask<InputClass>

    private fun print(content: Any?) = println(content)
    protected fun info(content: Any?) = print("Info: $content")
    protected fun debug(content: Any?) = print("Debug: $content")
    protected fun error(content: Any?) = print("Error: $content")
    protected fun critical(content: Any?) = print("Critical: $content")

    fun getResult(): Result {
        val input = this.getProcessedInput(Remote.getInput(year, day))

        val a = getSubTaskA()
        val b = getSubTaskB()

        val resultA = a.getResult(input)
        val resultB = b.getResult(input)

        val hasTestInput = getRawInputTest() != null
        if (hasTestInput) {
            val inputTestA = this.getProcessedInput(this.getRawInputTest()!!.getRawTestInputA())
            val inputTestB = this.getProcessedInput(this.getRawInputTest()!!.getRawTestInputB())
            val resultATest = a.getResultTest(inputTestA)
            val resultBTest = b.getResultTest(inputTestB)

            return Result(
                resultA, resultATest,
                resultB, resultBTest
            )
        }

        return Result(
            resultA, SubTask.Result(0, 0.0),
            resultB, SubTask.Result(0, 0.0)
        )
    }

    data class Result(
        val a: SubTask.Result,
        val aTest: SubTask.Result,

        val b: SubTask.Result,
        val bTest: SubTask.Result
    ) {
        override fun toString(): String {
            return """
                A: ${a.result} ${("%.2fs").format(a.timeS).replace(",", ".")}
                A_TEST: ${aTest.result} ${("%.2fs").format(aTest.timeS).replace(",", ".")}
                B: ${b.result} ${("%.2fs").format(b.timeS).replace(",", ".")}
                B_TEST: ${bTest.result} ${("%.2fs").format(bTest.timeS).replace(",", ".")}
            """.trimIndent()
        }
    }

}