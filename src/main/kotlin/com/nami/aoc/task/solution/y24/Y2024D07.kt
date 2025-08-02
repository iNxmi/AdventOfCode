package com.nami.aoc.task.solution.y24

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.pow

class Y2024D07 : Task<List<Y2024D07.Equation>>(2024, 7) {

    data class Equation(
        val solution: Long,
        val numbers: List<Int>
    )

    override fun getProcessedInput(raw: String): List<Equation> {
        val equations = mutableListOf<Equation>()

        val lines = raw.lines()
        for (line in lines) {
            val solution = line.split(":")[0].trim().toLong()
            val numbers = line.split(":")[1].trim().split(" ").map { it.trim().toInt() }.toList()
            equations.add(Equation(solution, numbers))
        }

        return equations
    }

    private fun convertIntToBase(i: Int, base: Int): String {
        val chars: CharArray = ("012").toCharArray()

        var i = i
        val builder = StringBuilder()
        do {
            builder.append(chars[i % base])
            i /= base
        } while (i > 0)
        return builder.reverse().toString()
    }

    override fun getPartA() = object : Part<List<Equation>>(
        year, day, Type.A,
        bonus = 3.0
    ) {
        override fun solve(input: List<Equation>): Any? {
            //0 = +
            //1 = *

            val possibleEquations = mutableListOf<Equation>()
            for (equation in input) {
                val bit = equation.numbers.size - 1
                val highestNumber = ((2.0).pow(bit.toDouble()) - 1).toInt()

                for (i in 0..highestNumber) {
                    val binary = Integer.toBinaryString(i).padStart(bit, '0').toCharArray()

                    var solution: Long = equation.numbers[0].toLong()
                    var count = 0
                    for (operator in 1..<equation.numbers.size) {
                        val n = equation.numbers[operator]

                        if (binary[count] == '0') {
                            solution += n
                        } else if (binary[count] == '1') {
                            solution *= n
                        }

                        count++
                    }

                    if (solution == equation.solution) {
                        possibleEquations.add(equation)
                        break
                    }

                }

            }

            return possibleEquations.sumOf { it.solution }
        }
    }

    override fun getPartB() = object : Part<List<Equation>>(
        year, day, Type.B,
        bonus = 7.0
    ) {
        override fun solve(input: List<Equation>): Any? {
            //0 = +
            //1 = *
            //1 = || -> concat

            val possibleEquations = mutableListOf<Equation>()
            for (equation in input) {
                val bit = equation.numbers.size - 1
                val highestNumber = ((3.0).pow(bit.toDouble()) - 1).toInt()

                for (i in 0..highestNumber) {
                    val trinary = convertIntToBase(i, 3).padStart(bit, '0').toCharArray()

                    var solution: Long = equation.numbers[0].toLong()
                    var count = 0
                    for (operator in 1..<equation.numbers.size) {
                        val n = equation.numbers[operator]

                        if (trinary[count] == '0') {
                            solution += n
                        } else if (trinary[count] == '1') {
                            solution *= n
                        } else if (trinary[count] == '2') {
                            solution = ("$solution$n").toLong()
                        }

                        count++
                    }

                    if (solution == equation.solution) {
                        possibleEquations.add(equation)
                        break
                    }

                }

            }

            return possibleEquations.sumOf { it.solution }
        }
    }

}