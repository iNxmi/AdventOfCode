package com.nami.task.solutions.y15

import com.nami.task.Task
import com.nami.task.test.TestInput
import com.nami.task.test.TestInputSimplex

class Y15D15 : Task<Set<Y15D15.Ingredient>>(2015, 15) {

    override fun getRawTestInput(): TestInput = TestInputSimplex(
        """
        Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
        Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
        """.trimIndent()
    )

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

    private val teaspoons = 100
    private fun findOptimal(ingredients: Set<Ingredient>) {

    }

    override fun solveA(input: Set<Ingredient>): Any? = null
    override fun solveB(input: Set<Ingredient>): Any? = null

}

fun main() = println(Y15D15().solve())