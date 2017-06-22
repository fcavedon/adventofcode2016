package com.shopify.adventofcode.day4

/**
 * Created by cavedon on 2017-06-19.
 */

fun main(args: Array<String>) {

    println(input.map { Room(it) }.filter { it.isValid }.sumBy { it.sectorId })
}


data class Room(val input: String) {

    val isValid: Boolean
    val checksum = input.split("[").last().replace("]", "")
    val sectorId: Int = buildString { input.filter { it.isDigit() }.map { append(it) } }.toInt()

    init {
        val groups: Map<Char, Int>

        groups = input.replace(checksum, "").groupingBy { it }.eachCount().toSortedMap()
        isValid = (checksum == buildString {
            groups.keys.sortedByDescending { groups[it] }.filter { it.isLetter() }.map { append(it) }
        }.take(5))
    }
}