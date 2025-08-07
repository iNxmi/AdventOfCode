package com.nami.aoc.task.solution.y15

import com.nami.aoc.print
import com.nami.aoc.task.Part
import com.nami.aoc.task.Task

class Y2015D22 : Task<Y2015D22.Entity>(2015, 22) {

    data class Entity(var health: Int, val damage: Int) {
        constructor(entity: Entity) : this(entity.health, entity.damage)
    }

    enum class Spell(
        val cost: Int,
        val duration: Int,
        val damage: Int,
        val armor: Int,
        val health: Int,
        val mana: Int
    ) {
        MAGIC_MISSILE(53, 0, 4, 0, 0, 0),
        DRAIN(73, 0, 2, 0, 2, 0),
        SHIELD(113, 6, 0, 7, 0, 0),
        POISON(173, 6, 3, 0, 0, 0),
        RECHARGE(229, 5, 0, 0, 0, 101);
    }

    data class State(
        val player: Entity,
        val boss: Entity,
        val spells: List<Spell>,
        val durations: Map<Spell, Int>
    ) {
        constructor(state: State) : this(
            Entity(state.player),
            Entity(state.boss),
            state.spells.toList(),
            state.durations.toMap()
        )
    }

//    private fun getSimulations(states: List<State>): List<State> {
//        for (spell in Spell.entries) {
//            val state = State(start)
//        }
//    }

    override fun getProcessedInput(raw: String): Entity {
        val values = Regex("\\d+").findAll(raw).toList()
        val attributes = values.map { it.value.toInt() }
        return Entity(attributes[0], attributes[1])
    }

    override fun getPartA() = object : Part<Entity>(
        this, Suffix.A
    ) {
        override fun solve(input: Entity) = null
    }

    override fun getPartB() = object : Part<Entity>(
        this, Suffix.B
    ) {
        override fun solve(input: Entity) = null
    }

}

//fun main() = Y2015D22().getTestVerifications().print()
fun main() = Y2015D22().getResults().print()
//fun main() = Y2015D22().getVerifications().print()