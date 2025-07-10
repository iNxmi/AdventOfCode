package com.nami

fun main() {
    val start = ('a').code

    val base = 'v'
    val offset = base.code - ('a').code

    for (i in 0..100)
        println("$i -> ${('a'.code + ((i + offset) % 26)).toChar()}")
}
