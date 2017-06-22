package com.shopify.adventofcode.day3

/**
 * Created by cavedon on 2017-06-07.
 */
fun main(args: Array<String>) {
    println(input.map { line ->
        val split = line.split("  ")
        Triangle(split[1].toInt(), split[2].toInt(), split[3].toInt()).isValid()
    }.filter { it }
            .count())
}

data class Triangle(val x: Int, val y: Int, val z: Int) {

    fun isValid(): Boolean {
        return ((x + y > z) && (x + z > y) && (y + z > x))
    }
}