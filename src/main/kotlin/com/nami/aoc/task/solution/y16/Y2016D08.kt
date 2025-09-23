package com.nami.aoc.task.solution.y16

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.solution.common.ASCIILetter
import org.joml.Vector2i


class Y2016D08 : Task<List<Y2016D08.Instruction>>(2016, 8) {

    companion object {
        val DISPLAY_SIZE = Vector2i(50, 6)
    }

    interface Instruction {
        fun execute(display: MutableSet<Vector2i>)
    }

    class InstructionSet(val width: Int, val height: Int) : Instruction {
        override fun execute(display: MutableSet<Vector2i>) {
            for (y in 0 until height)
                for (x in 0 until width)
                    display.add(Vector2i(x, y))
        }
    }

    class InstructionShiftColumn(val x: Int, val amount: Int) : Instruction {
        override fun execute(display: MutableSet<Vector2i>) {
            val pixels = display.filter { it.x == x }
            display.removeAll(pixels)
            val updated = pixels.map {
                val value = (it.y + amount) % DISPLAY_SIZE.y
                Vector2i(it.x, value)
            }
            display.addAll(updated)
        }
    }

    class InstructionShiftRow(val y: Int, val amount: Int) : Instruction {
        override fun execute(display: MutableSet<Vector2i>) {
            val pixels = display.filter { it.y == y }
            display.removeAll(pixels)
            val updated = pixels.map {
                val value = (it.x + amount) % DISPLAY_SIZE.x
                Vector2i(value, it.y)
            }
            display.addAll(updated)
        }
    }

    override fun getProcessedInput(raw: String) = raw.lines().map { line ->
        val arguments = Regex("\\d+").findAll(line).toList()
        val arg0 = arguments[0].value.toInt()
        val arg1 = arguments[1].value.toInt()

        if (line.startsWith("rect")) {
            InstructionSet(arg0, arg1)
        } else if (line.startsWith("rotate column")) {
            InstructionShiftColumn(arg0, arg1)
        } else if (line.startsWith("rotate row")) {
            InstructionShiftRow(arg0, arg1)
        } else {
            throw IllegalStateException("Unexpected line '$line'")
        }
    }

    override fun getPartA() = object : Part<List<Instruction>>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: List<Instruction>): Any {
            val display = mutableSetOf<Vector2i>()
            input.forEach { it.execute(display) }
            return display.size
        }
    }

    override fun getPartB() = object : Part<List<Instruction>>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: List<Instruction>): Any {
            val display = mutableSetOf<Vector2i>()
            input.forEach { it.execute(display) }

            val letters = mutableListOf<String>()
            for (i in 0 until DISPLAY_SIZE.x / ASCIILetter.LETTER_SIZE.x) {
                val sb = StringBuilder()
                for (y in 0 until ASCIILetter.LETTER_SIZE.y) {
                    for (xOffset in 0 until ASCIILetter.LETTER_SIZE.x) {
                        val x = i * ASCIILetter.LETTER_SIZE.x + xOffset
                        val char = if (display.contains(Vector2i(x, y))) '@' else '.'
                        sb.append(char)
                    }
                    sb.appendLine()
                }
                letters.add(sb.toString().trimIndent())
            }

            return letters.joinToString("") { ASCIILetter.getCharacter(it).toString() }
        }

        override fun test(input: List<Instruction>) = null
    }

}
