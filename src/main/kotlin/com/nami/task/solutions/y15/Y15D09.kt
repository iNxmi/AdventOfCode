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

    override fun getProcessedInput(raw: String): Input {
        val nodes = mutableSetOf<Node>()
        val edges = mutableMapOf<Pair<Node, Node>, Int>()

        val str = raw.lowercase().replace("to", ":").replace(" ", "")
        str.lines().forEach { line ->
            val split = line.split("=")
            val distance = split[1].toInt()

            val splitLocations = split[0].split(":")
            val nodeA = Node(splitLocations[0])
            val nodeB = Node(splitLocations[1])

            nodes.add(nodeA)
            val connectionA = Pair(nodeA, nodeB)
            edges[connectionA] = distance

            nodes.add(nodeB)
            val connectionB = Pair(nodeB, nodeA)
            edges[connectionB] = distance
        }

        return Input(nodes, edges)
    }

    private fun permutations(nodes: List<Node>, level: Int = 0): Set<List<Node>> {
        if (nodes.size - level <= 1)
            return setOf(nodes)

        val result = mutableSetOf<List<Node>>()
        for (i in level..<nodes.size) {
            for (j in i..<nodes.size) {
                val swapped = nodes.toMutableList()
                swapped[i] = nodes[j]
                swapped[j] = nodes[i]

                val permutations = permutations(swapped, level + 1)
                result.addAll(permutations)
            }
        }

        return result
    }

    private fun distances(input: Input, permutations: Set<List<Node>>): Map<Int, List<Node>> {
        val result = mutableMapOf<Int, List<Node>>()
        permutations.forEach { list ->
            var sum = 0
            for (i in 0..<list.size - 1) {
                val start = list[i]
                val stop = list[i + 1]
                val connection = Pair(start, stop)
                sum += input.connections[connection]!!
            }

            result[sum] = list
        }

        return result
    }

    override fun solveA(input: Input): Any {
        val permutations = permutations(input.nodes.toList())
        val distances = distances(input, permutations)

        val result = distances.toSortedMap().firstEntry()
        return result.key
    }

    override fun solveB(input: Input): Any {
        val permutations = permutations(input.nodes.toList())
        val distances = distances(input, permutations)

        val result = distances.toSortedMap().reversed().firstEntry()
        return result.key
    }

}

fun main() = println(Y15D09().solve())
