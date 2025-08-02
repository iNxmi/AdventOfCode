package com.nami.aoc.task.solution.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D16 : Task<Set<Y2015D16.Aunt>>(2015, 16) {

    data class Aunt(
        val id: Int,

        val children: Int?,
        val cats: Int?,
        val samoyeds: Int?,
        val pomeranians: Int?,
        val akitas: Int?,
        val vizslas: Int?,
        val goldfish: Int?,
        val trees: Int?,
        val cars: Int?,
        val perfumes: Int?
    )

    override fun getProcessedInput(raw: String): Set<Aunt> {
        val string = raw
            .replace(("Sue \\d*:").toRegex(), "")
            .replace(" ", "")

        val set = mutableSetOf<Aunt>()
        string.lines().withIndex().forEach { (index, line) ->
            val split = line.split(",")

            var children: Int? = null
            var cats: Int? = null
            var samoyeds: Int? = null
            var pomeranians: Int? = null
            var akitas: Int? = null
            var vizslas: Int? = null
            var goldfish: Int? = null
            var trees: Int? = null
            var cars: Int? = null
            var perfumes: Int? = null

            split.forEach { part ->
                val split2 = part.split(":")
                val data = split2[1].toInt()
                when (split2[0]) {
                    "children" -> children = data
                    "cats" -> cats = data
                    "samoyeds" -> samoyeds = data
                    "pomeranians" -> pomeranians = data
                    "akitas" -> akitas = data
                    "vizslas" -> vizslas = data
                    "goldfish" -> goldfish = data
                    "trees" -> trees = data
                    "cars" -> cars = data
                    "perfumes" -> perfumes = data
                }
            }

            set.add(
                Aunt(
                    index + 1, children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes
                )
            )
        }

        return set
    }

    override fun getPartA() = object : Part<Set<Aunt>>(
        year, day, Type.A,
        bonus = 5.0
    ) {
        override fun solve(input: Set<Aunt>): Any {
            val aunts = input.asSequence()
                .filter { it.children == null || it.children == 3 }
                .filter { it.cats == null || it.cats == 7 }
                .filter { it.samoyeds == null || it.samoyeds == 2 }
                .filter { it.pomeranians == null || it.pomeranians == 3 }
                .filter { it.akitas == null || it.akitas == 0 }
                .filter { it.vizslas == null || it.vizslas == 0 }
                .filter { it.goldfish == null || it.goldfish == 5 }
                .filter { it.trees == null || it.trees == 3 }
                .filter { it.cars == null || it.cars == 2 }
                .filter { it.perfumes == null || it.perfumes == 1 }
                .toList()

            if (aunts.size > 1)
                throw IllegalStateException("Error (A): More than 1 aunt Sue found -> $aunts")

            return aunts.first().id
        }
    }

    override fun getPartB() = object : Part<Set<Aunt>>(
        year, day, Type.B,
        bonus = 10.0
    ) {
        override fun solve(input: Set<Aunt>): Any {
            val aunts = input.asSequence()
                .filter { it.children == null || it.children == 3 }
                .filter { it.cats == null || it.cats > 7 }
                .filter { it.samoyeds == null || it.samoyeds == 2 }
                .filter { it.pomeranians == null || it.pomeranians < 3 }
                .filter { it.akitas == null || it.akitas == 0 }
                .filter { it.vizslas == null || it.vizslas == 0 }
                .filter { it.goldfish == null || it.goldfish < 5 }
                .filter { it.trees == null || it.trees > 3 }
                .filter { it.cars == null || it.cars == 2 }
                .filter { it.perfumes == null || it.perfumes == 1 }
                .toList()

            if (aunts.size > 1)
                throw IllegalStateException("Error (B): More than 1 aunt Sue found -> $aunts")

            return aunts.first().id
        }
    }

}