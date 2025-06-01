package com.nami.task

data class Result(
    val id: Int, val year: Int, val day: Int,

    val a: Any?, val aTimeS: Double,
    val b: Any?, val bTimeS: Double,

    val aTest: Any?, val bTest: Any?
)