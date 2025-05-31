package com.nami.assignments.y15

import com.nami.Assignment
import com.nami.test.TestInputSimplex

class Y15D09 : Assignment<Y15D09.Map>(2015, 9) {

    override fun getRawTestInput() = TestInputSimplex(
        """
        London to Dublin = 464
        London to Belfast = 518
        Dublin to Belfast = 141
        """.trimIndent()
    )

    data class Node(val name: String)
    data class Connection(val nodeA: Node, val nodeB: Node, val distance: Int)
    data class Route(val connections: Set<Connection>)
    data class Map(val nodes: Set<Node>, val connections: Set<Connection>) {

        fun getPossibleRoutes(): Set<Route> {
            TODO()
        }

    }

    override fun getProcessedInput(raw: String): Map {
        val nodes = mutableSetOf<Node>()
        val connections = mutableSetOf<Connection>()

        val str = raw.lowercase().replace("to", ":").replace(" ", "")
        str.lines().forEach { line ->
            val split = line.split("=")
            val distance = split[1].toInt()

            val splitLocations = split[0].split(":")
            val nodeA = Node(splitLocations[0])
            val nodeB = Node(splitLocations[1])

            nodes.add(nodeA)
            nodes.add(nodeB)

            connections.add(Connection(nodeA, nodeB, distance))
        }

        return Map(nodes, connections)
    }

    override fun solveA(input: Map): Any? = null

    override fun solveB(input: Map): Any? = null

}

fun main() = println(Y15D09().solve())
