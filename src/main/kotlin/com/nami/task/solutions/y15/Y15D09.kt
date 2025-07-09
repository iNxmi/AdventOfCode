package com.nami.task.solutions.y15

import com.nami.permutations
import com.nami.task.Part
import com.nami.task.Task
import com.nami.task.input.InputSimplex

class Y15D09 : Task<Y15D09.Input>(2015, 9) {

    override fun getRawInputTest() = InputSimplex(
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

        val string = raw.lowercase()
            .replace("to", ":")
            .replace(" ", "")
        string.lines().forEach { line ->
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

    private fun distances(input: Input): Map<Int, List<Node>> {
        val result = mutableMapOf<Int, List<Node>>()
        val permutations = input.nodes.toList().permutations()
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

    override fun getPartA() = object : Part<Input> {
        override fun solve(input: Input): Any? {
            val distances = distances(input)
            val result = distances.toSortedMap().firstEntry()
            return result.key
        }
        override fun bonus() = 5.0
    }

    override fun getPartB() = object : Part<Input> {
        override fun solve(input: Input): Any? {
            val distances = distances(input)
            val result = distances.toSortedMap().reversed().firstEntry()
            return result.key
        }
        override fun bonus() = 10.0
    }

}

fun main() = Y15D09().printResult()
//fun main() = Y15D09().printVerification()
