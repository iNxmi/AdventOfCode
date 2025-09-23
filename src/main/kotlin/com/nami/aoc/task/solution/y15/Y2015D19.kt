package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D19 : Task<Y2015D19.Input>(2015, 19) {

    data class Rule(val pattern: String, val replacement: String) {
        val regex = Regex(pattern)
    }

    data class Input(val molecule: String, val rules: Set<Rule>)

    override fun getProcessedInput(raw: String): Input {
        val split = raw.replace("Rn", "(")
            .replace("Ar", ")")
            .split("\n\n")

        val molecule = split[1].trim()
        val rules = split[0].trim().lines().map { line ->
            val split = line.split(" => ")
            val pattern = split[0].trim()
            val replacement = split[1].trim()

            Rule(pattern, replacement)
        }.toSet()

        return Input(molecule, rules)
    }

    private fun getPossibleMolecules(molecule: String, rules: Set<Rule>): Set<String> {
        val result = mutableSetOf<String>()
        rules.forEach { rule ->
            val occurrences = rule.regex.findAll(molecule)
            for (occurrence in occurrences) {
                val range = occurrence.range
                val replaced = molecule.replaceRange(range, rule.replacement)
                result.add(replaced)
            }
        }

        return result
    }

    override fun getPartA() = object : Part<Input>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: Input) = getPossibleMolecules(input.molecule, input.rules).size
    }

    override fun getPartB() = object : Part<Input>(
        this, Suffix.B,
        comment = "https://www.reddit.com/r/adventofcode/comments/3xflz8/comment/cy4k8ca/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button"
    ) {
        override fun solve(input: Input): Any {
            val reversed = input.rules.map { rule -> Rule(rule.replacement, rule.pattern) }
            reversed.forEach { rule -> log.debug { rule } }

            return "TODO"
        }
    }

}

//fun main() = Y2015D19().getTestVerifications().print()
fun main() = Y2015D19().getResults().print()
//fun main() = Y2015D19().getVerifications().print()