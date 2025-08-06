package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D19 : Task<Y2015D19.Input>(2015, 19) {

    data class Input(val molecule: String, val replacements: Map<String, Set<String>>)

    override fun getProcessedInput(raw: String): Input {
        val split = raw.split("\n\n")

        val replacements = mutableMapOf<String, MutableSet<String>>()
        split[0].trim().lines().map { line ->
            val split = line.split(" => ")
            val base = split[0].trim()
            val replacement = split[1].trim()

            if (!replacements.containsKey(base))
                replacements[base] = mutableSetOf()

            replacements[base]!!.add(replacement)
        }
        val molecule = split[1].trim()

        return Input(molecule, replacements)
    }

    private fun getPossibleMolecules(molecule: String, replacements: Map<String, Set<String>>): Set<String> {
        val result = mutableSetOf<String>()
        for ((base, substitutes) in replacements) {
            val occurrences = Regex(base).findAll(molecule)
            for (occurrence in occurrences) {
                for (substitute in substitutes) {
                    val range = occurrence.range
                    val replaced = molecule.replaceRange(range, substitute)
                    result.add(replaced)
                }
            }
        }
        return result
    }

//    private fun getPossibleRecipesCount(molecule: String, replacements: Map<String, Set<String>>): Int {
//        val reversed = mutableMapOf<String, MutableSet<String>>()
//        replacements.forEach { (base, substitutes) ->
//            substitutes.forEach { substitute ->
//                if (!reversed.containsKey(substitute))
//                    reversed[substitute] = mutableSetOf()
//
//                reversed[substitute]!!.add(base)
//            }
//        }
//
//        var temp = setOf(molecule)
//        var count = 0
//        while (!temp.contains("e")) {
//            log.debug { temp}
//            temp = temp.flatMap { string ->
//                getPossibleMolecules(string, reversed)
//            }.filterNot { "e" in it && it.length > 1 }.toSet()
//            log.debug { temp}
//            count++
//        }
//
//        return count
//    }

    override fun getPartA() = object : Part<Input>(
        this, Suffix.A
    ) {
        override fun solve(input: Input) = getPossibleMolecules(input.molecule, input.replacements).size
    }

    override fun getPartB() = object : Part<Input>(
        this, Suffix.B
    ) {
        override fun solve(input: Input) = null
    }

}

fun main() = Y2015D19().getTestVerifications().print()
//fun main() = Y2015D19().getResults().print()
//fun main() = Y2015D19().getVerifications().print()