package com.nami.task

data class UID(
    val year: Int,
    val day: Int,
    val part: Part,
    val id: Int = year * 1000 + day * 10 + part.ordinal
) {
    enum class Part { ROOT, A, B, A_TEST, B_TEST }

    companion object {
        fun from(uid: Int): UID {
            val year = uid / 1000
            val day = (uid / 10) % 100
            val ordinal = uid % 10
            val part = Part.entries[ordinal]

            return UID(year, day, part)
        }
    }
}