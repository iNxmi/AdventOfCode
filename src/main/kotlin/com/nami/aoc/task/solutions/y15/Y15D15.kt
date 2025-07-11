package com.nami.aoc.task.solutions.y15

import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import com.nami.aoc.task.input.InputSimplex

/*
    STARS AND BARS
    TODO i am not very satisfied with the current solution so i want to rewrite it more universally
 */

class Y15D15 : Task<Set<Y15D15.Ingredient>>(2015, 15) {

    override fun getRawInputTest() = InputSimplex(
        """
        Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
        Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
        """.trimIndent()
    )

    data class Recipe(val ingredients: Map<Ingredient, Int>) {
        fun score(): Int {
            val capacity = ingredients.entries.sumOf { (key, value) -> key.capacity * value }.coerceAtLeast(0)
            val durability = ingredients.entries.sumOf { (key, value) -> key.durability * value }.coerceAtLeast(0)
            val flavor = ingredients.entries.sumOf { (key, value) -> key.flavor * value }.coerceAtLeast(0)
            val texture = ingredients.entries.sumOf { (key, value) -> key.texture * value }.coerceAtLeast(0)

            return capacity * durability * flavor * texture
        }

        fun calories() = ingredients.entries.sumOf { (key, value) -> key.calories * value }
    }

    data class Ingredient(
        val name: String,
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
    )

    override fun getProcessedInput(raw: String): Set<Ingredient> {
        val string = raw
            .replace(":", ",")
            .replace("capacity", "")
            .replace("durability", "")
            .replace("flavor", "")
            .replace("texture", "")
            .replace("calories", "")
            .replace(" ", "")

        val set = mutableSetOf<Ingredient>()
        string.lines().forEach { line ->
            val split = line.split(",")

            val name = split[0]
            val capacity = split[1].toInt()
            val durability = split[2].toInt()
            val flavor = split[3].toInt()
            val texture = split[4].toInt()
            val calories = split[5].toInt()

            set.add(Ingredient(name, capacity, durability, flavor, texture, calories))
        }

        return set
    }

    override fun getPartA() = object : Part<Set<Ingredient>> {
        override fun solve(input: Set<Ingredient>): Int {
            var recipe = Recipe(mapOf())

            for (x in 0..100)
                for (y in 0..100 - x)
                    for (z in 0..100 - x - y) {
                        val w = 100 - x - y - z

                        val map = mutableMapOf<Ingredient, Int>()
                        map[input.elementAt(0)] = x
                        map[input.elementAt(1)] = y
                        map[input.elementAt(2)] = z
                        map[input.elementAt(3)] = w

                        val current = Recipe(map)

                        if (current.score() > recipe.score())
                            recipe = current
                    }

            return recipe.score()
        }

        override fun test(input: Set<Ingredient>): Int {
            var recipe = Recipe(mapOf())

            for (x in 0..100) {
                val y = 100 - x

                val map = mutableMapOf<Ingredient, Int>()
                map[input.elementAt(0)] = x
                map[input.elementAt(1)] = y

                val current = Recipe(map)

                if (current.score() > recipe.score())
                    recipe = current
            }

            return recipe.score()
        }

        override fun bonus() = 5.0
        override fun comment() = "Needs Improvement (Stars and Bars)"
    }

    override fun getPartB() = object : Part<Set<Ingredient>> {
        override fun solve(input: Set<Ingredient>): Int {
            var recipe = Recipe(mapOf())

            for (x in 0..100)
                for (y in 0..100 - x)
                    for (z in 0..100 - x - y) {
                        val w = 100 - x - y - z

                        val map = mutableMapOf<Ingredient, Int>()
                        map[input.elementAt(0)] = x
                        map[input.elementAt(1)] = y
                        map[input.elementAt(2)] = z
                        map[input.elementAt(3)] = w

                        val current = Recipe(map)

                        if (current.calories() == 500 && current.score() > recipe.score())
                            recipe = current
                    }

            return recipe.score()
        }

        override fun test(input: Set<Ingredient>): Int {
            var recipe = Recipe(mapOf())

            for (x in 0..100) {
                val y = 100 - x

                val map = mutableMapOf<Ingredient, Int>()
                map[input.elementAt(0)] = x
                map[input.elementAt(1)] = y

                val current = Recipe(map)

                if (current.calories() == 500 && current.score() > recipe.score())
                    recipe = current
            }

            return recipe.score()
        }

        override fun bonus() = 10.0
        override fun comment() = "Needs Improvement (Stars and Bars)"
    }

}

fun main() = Y15D15().printResults()