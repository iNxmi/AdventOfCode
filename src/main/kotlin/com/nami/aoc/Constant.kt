package com.nami.aoc

val YEAR_RANGE = 2015..2025
val DAY_RANGE = mutableMapOf<Int, IntRange>().apply {

    //2015 to 2024 each 25 days
    for(year in 2015..2024)
        put(year, 1..25)

    //Since 2025 only 12 days
    //https://adventofcode.com/2025/about#faq_num_days
    for(year in 2025..2025)
        put(year, 1..12)

}