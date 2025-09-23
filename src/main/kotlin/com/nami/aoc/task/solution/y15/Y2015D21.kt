package com.nami.aoc.task.solution.y15

import com.nami.aoc.combinations
import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task
import kotlin.math.max


class Y2015D21 : Task<Y2015D21.Entity>(2015, 21) {

    data class Entity(var health: Int, val damage: Int, val armor: Int) {
        constructor(entity: Entity) : this(entity.health, entity.damage, entity.armor)
    }

    private interface Item {
        val cost: Int;
        val damage: Int;
        val armor: Int
    }

    private enum class Weapon(override val cost: Int, override val damage: Int, override val armor: Int) : Item {
        DAGGER(8, 4, 0),
        SHORTSWORD(10, 5, 0),
        WARHAMMER(25, 6, 0),
        LONGSWORD(40, 7, 0),
        GREATAXE(74, 8, 0)
    }

    private enum class Armor(override val cost: Int, override val damage: Int, override val armor: Int) : Item {
        LEATHER(13, 0, 1),
        CHAINMAIL(31, 0, 2),
        SPLINTMAIL(53, 0, 3),
        BANDEDMAIL(75, 0, 4),
        PLATEMAIL(102, 0, 5)
    }

    private enum class Ring(override val cost: Int, override val damage: Int, override val armor: Int) : Item {
        DAMAGE_1(25, 1, 0),
        DAMAGE_2(50, 2, 0),
        DAMAGE_3(100, 3, 0),
        DEFENSE_1(20, 0, 1),
        DEFENSE_2(40, 0, 2),
        DEFENSE_3(80, 0, 3)
    }

    private fun getCombinations(): Set<Set<Item>> {
        val items = mutableSetOf<Item>().apply {
            addAll(Weapon.entries.toTypedArray())
            addAll(Armor.entries.toTypedArray())
            addAll(Ring.entries.toTypedArray())
        }

        return items.combinations().filter { combination ->
            combination.count { item -> item is Weapon } == 1 &&
                    combination.count { item -> item is Armor } in 0..1 &&
                    combination.count { item -> item is Ring } in 0..2
        }.map { it.toSet() }.toSet()
    }

    //returns true if player wins
    private fun simulate(player: Entity, boss: Entity): Boolean {
        var turn = true
        while (boss.health > 0 && player.health > 0) {
            val attacker: Entity = if (turn) player else boss
            val defender: Entity = if (turn) boss else player

            defender.health -= max(attacker.damage - defender.armor, 1)

            turn = !turn
        }

        return player.health > 0
    }

    override fun getProcessedInput(raw: String): Entity {
        val values = Regex("\\d+").findAll(raw).toList()
        val attributes = values.map { it.value.toInt() }
        return Entity(attributes[0], attributes[1], attributes[2])
    }

    override fun getPartA() = object : Part<Entity>(
        this, Suffix.A,
        bonus = 5.0
    ) {
        override fun solve(input: Entity): Any? {
            val combinations = getCombinations()

            val simulations = combinations.filter { combination ->
                val damage = combination.sumOf { item -> item.damage }
                val armor = combination.sumOf { item -> item.armor }
                val player = Entity(100, damage, armor)
                val boss = Entity(input)

                simulate(player, boss)
            }

            val cheapest = simulations.minBy { combination -> combination.sumOf { item -> item.cost } }
            return cheapest.sumOf { item -> item.cost }
        }
    }

    override fun getPartB() = object : Part<Entity>(
        this, Suffix.B,
        bonus = 10.0
    ) {
        override fun solve(input: Entity): Any? {
            val combinations = getCombinations()
            val simulations = combinations.filter { combination ->
                val damage = combination.sumOf { item -> item.damage }
                val armor = combination.sumOf { item -> item.armor }
                val player = Entity(100, damage, armor)
                val boss = Entity(input)

                !simulate(player, boss)
            }

            val expensive = simulations.maxBy { combination -> combination.sumOf { item -> item.cost } }
            return expensive.sumOf { item -> item.cost }
        }
    }

}

//fun main() = Y2015D21().getTestVerifications().print()
//fun main() = Y2015D21().getResults().print()
fun main() = Y2015D21().getVerifications().print()