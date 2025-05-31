package com.nami.assignments.y15

import com.nami.Assignment

class Y15D16 : Assignment<Set<Y15D16.Aunt>, Int>(2015, 16) {

    override fun getRawTestInput() = null

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
                val data = split2[1]
                when (split2[0]) {
                    "children" -> children = data.toInt()
                    "cats" -> cats = data.toInt()
                    "samoyeds" -> samoyeds = data.toInt()
                    "pomeranians" -> pomeranians = data.toInt()
                    "akitas" -> akitas = data.toInt()
                    "vizslas" -> vizslas = data.toInt()
                    "goldfish" -> goldfish = data.toInt()
                    "trees" -> trees = data.toInt()
                    "cars" -> cars = data.toInt()
                    "perfumes" -> perfumes = data.toInt()
                }
            }

            val aunt =
                Aunt(index + 1, children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes)
            set.add(aunt)
        }

        return set
    }

    override fun solveA(input: Set<Aunt>): Int {
        val aunts = input
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

        if (aunts.size > 1)
            throw IllegalStateException("Error (A): More than 1 aunt Sue found -> $aunts")

        return aunts.first().id
    }

    override fun solveB(input: Set<Aunt>): Int {
        val aunts = input
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

        if (aunts.size > 1)
            throw IllegalStateException("Error (B): More than 1 aunt Sue found -> $aunts")

        return aunts.first().id
    }

}

fun main() = println(Y15D16().solve())