package com.nami.aoc.task.solution.common

import org.joml.Vector2i

enum class Direction(val vector: Vector2i) {

    NORTH(Vector2i(0, 1)),
    EAST(Vector2i(1, 0)),
    SOUTH(Vector2i(0, -1)),
    WEST(Vector2i(-1, 0)),

    NORTH_WEST(Vector2i(-1, 1)),
    NORTH_EAST(Vector2i(1, 1)),
    SOUTH_EAST(Vector2i(1, -1)),
    SOUTH_WEST(Vector2i(-1, -1)),

    UP(Vector2i(0, 1)),
    RIGHT(Vector2i(1, 0)),
    DOWN(Vector2i(0, -1)),
    LEFT(Vector2i(-1, 0)),

}