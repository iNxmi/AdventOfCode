package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.absoluteValue

class Y2015D23 : Task<List<String>>(2015, 23) {

    override fun getProcessedInput(raw: String) = raw.lines()

    private fun simulate(registers: MutableMap<String, UInt>, instructions: List<String>) {
        if(!registers.containsKey("a"))
            registers["a"] = 0u
        if(!registers.containsKey("b"))
            registers["b"] = 0u
        if(!registers.containsKey("pc"))
            registers["pc"] = 0u

        while (registers["pc"]!! < instructions.size.toUInt()) {
            val instruction = instructions[registers["pc"]!!.toInt()]
            val split = instruction.replace(",", "").split(" ")
            val opcode = split[0]
            val arg0 = split[1]
            when (opcode) {
                "hlf" -> {
                    registers[arg0] = registers[arg0]!! / 2u
                    registers["pc"] = registers["pc"]!! + 1u
                }

                "tpl" -> {
                    registers[arg0] = registers[arg0]!! * 3u
                    registers["pc"] = registers["pc"]!! + 1u
                }

                "inc" -> {
                    registers[arg0] = registers[arg0]!! + 1u
                    registers["pc"] = registers["pc"]!! + 1u
                }

                "jmp" -> {
                    val offset = arg0.toInt()
                    val uOffset = offset.absoluteValue.toUInt()
                    if ("+" in instruction) {
                        registers["pc"] = registers["pc"]!! + uOffset
                    } else if ("-" in instruction) {
                        registers["pc"] = registers["pc"]!! - uOffset
                    }
                }

                "jie" -> {
                    val register = arg0
                    if (registers[register]!! % 2u == 0u) {
                        val offset = split[2].toInt()
                        val uOffset = offset.absoluteValue.toUInt()
                        if ("+" in instruction) {
                            registers["pc"] = registers["pc"]!! + uOffset
                        } else if ("-" in instruction) {
                            registers["pc"] = registers["pc"]!! - uOffset
                        }
                    } else {
                        registers["pc"] = registers["pc"]!! + 1u
                    }
                }

                "jio" -> {
                    val register = arg0
                    if (registers[register]!! == 1u) {
                        val offset = split[2].toInt()
                        val uOffset = offset.absoluteValue.toUInt()
                        if ("+" in instruction) {
                            registers["pc"] = registers["pc"]!! + uOffset
                        } else if ("-" in instruction) {
                            registers["pc"] = registers["pc"]!! - uOffset
                        }
                    } else {
                        registers["pc"] = registers["pc"]!! + 1u
                    }
                }
            }
        }
    }

    override fun getPartA() = object : Part<List<String>>(
        this, Suffix.A
    ) {
        override fun solve(input: List<String>): Any {
            val registers = mutableMapOf<String, UInt>()
            simulate(registers, input)
            return registers["b"]!!
        }

        override fun test(input: List<String>): Any {
            val registers = mutableMapOf<String, UInt>()
            simulate(registers, input)
            return registers["a"]!!
        }
    }

    override fun getPartB() = object : Part<List<String>>(
        this, Suffix.B
    ) {
        override fun solve(input: List<String>): Any {
            val registers = mutableMapOf<String, UInt>()
            registers["a"] = 1u

            simulate(registers, input)
            return registers["b"]!!
        }
    }

}

//fun main() = Y2015D23().getTestVerifications().print()
//fun main() = Y2015D23().getResults().print()
fun main() = Y2015D23().getVerifications().print()