package com.shopify.adventofcode.day4

import com.shopify.adventofcode.ALPHA_CHARS

/**
 * Created by cavedon on 2017-06-19.
 */
fun main(args: Array<String>) {

    println(input.map { Room(it) }.find { it.isValid && it.decrypt().contains("north") })

}

fun Room.decrypt(): String = buildString {
    val name = input.replace("$sectorId[$checksum]", "")

    name.map {
        when (it) {
            '-' -> append(" ")
            else -> append(ALPHA_CHARS[(ALPHA_CHARS.indexOf(it) + sectorId) % 26])
        }
    }
}