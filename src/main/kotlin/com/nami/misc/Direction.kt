package com.nami.misc

import org.joml.Vector2i

enum class Direction(val vector: Vector2i) {
    NORTH_WEST(Vector2i(-1, 1)),
    NORTH(Vector2i(0, 1)),
    NORTH_EAST(Vector2i(1, 1)),

    EAST(Vector2i(1, 0)),

    SOUTH_EAST(Vector2i(1, -1)),
    SOUTH(Vector2i(0, -1)),
    SOUTH_WEST(Vector2i(-1, -1)),

    WEST(Vector2i(-1, 0))
}