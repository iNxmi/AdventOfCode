package com.nami.task.solutions.y15

import com.nami.task.Task
import com.nami.task.test.TestInputSimplex

class Y15D09 : Task<Y15D09.Input>(2015, 9) {

    override fun getRawTestInput() = TestInputSimplex(
        """
        London to Dublin = 464
        London to Belfast = 518
        Dublin to Belfast = 141
        """.trimIndent()
    )

    data class Node(val name: String)
    data class Input(val nodes: Set<Node>, val connections: Map<Pair<Node, Node>, Int>)

    private fun permutation(nodes: List<Node>, level: Int): Set<List<Node>> {
        if (nodes.size - level <= 1)
            return setOf(nodes)

        val result = mutableSetOf<List<Node>>()
        for (i in level..<nodes.size) {
            for (j in i..<nodes.size) {
                val swapped = nodes.toMutableList()
                swapped[i] = nodes[j]
                swapped[j] = nodes[i]

                val permutations = permutation(swapped, level + 1)
                result.addAll(permutations)
            }
        }

        return result
    }

    override fun getProcessedInput(raw: String): Input {
        val nodes = mutableSetOf<Node>()
        val connections = mutableMapOf<Pair<Node, Node>, Int>()

        val str = raw.lowercase().replace("to", ":").replace(" ", "")
        str.lines().forEach { line ->
            val split = line.split("=")
            val distance = split[1].toInt()

            val splitLocations = split[0].split(":")
            val nodeA = Node(splitLocations[0])
            val nodeB = Node(splitLocations[1])

            nodes.add(nodeA)
            val connectionA = Pair(nodeA, nodeB)
            connections[connectionA] = distance

            nodes.add(nodeB)
            val connectionB = Pair(nodeB, nodeA)
            connections[connectionB] = distance
        }

        return Input(nodes, connections)
    }

    override fun solveA(input: Input): Any {
        val possibilities = permutation(input.nodes.toList(), 0)

        val map = mutableMapOf<Int, List<Node>>()
        possibilities.forEach { list ->
            var sum = 0
            for (i in 0..<list.size - 1) {
                val start = list[i]
                val stop = list[i + 1]
                val connection = Pair(start, stop)
                sum += input.connections[connection]!!
            }

            map[sum] = list
        }

        val shortest = map.toSortedMap().firstEntry()
        println(shortest)

        return shortest.key
    }

    override fun solveB(input: Input): Any {
        val possibilities = permutation(input.nodes.toList(), 0)

        val map = mutableMapOf<Int, List<Node>>()
        possibilities.forEach { list ->
            var sum = 0
            for (i in 0..<list.size - 1) {
                val start = list[i]
                val stop = list[i + 1]
                val connection = Pair(start, stop)
                sum += input.connections[connection]!!
            }

            map[sum] = list
        }

        val longest = map.toSortedMap().reversed().firstEntry()
        println(longest)

        return longest.key
    }

}

fun main() = println(Y15D09().solve())
