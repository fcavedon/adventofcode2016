package com.shopify.adventofcode.day3

import com.shopify.adventofcode.asInt

/**
 * Created by cavedon on 2017-06-07.
 */
fun main(args: Array<String>) {

    var idx = -1
    var valid = 0

    while (idx + 1 < input.size) {
        val line1 = input[++idx].split("  ")
        val line2 = input[++idx].split("  ")
        val line3 = input[++idx].split("  ")

        val triangle1 = Triangle(line1[1].asInt(), line2[1].asInt(), line3[1].asInt())
        val triangle2 = Triangle(line1[2].asInt(), line2[2].asInt(), line3[2].asInt())
        val triangle3 = Triangle(line1[3].asInt(), line2[3].asInt(), line3[3].asInt())

        valid += listOf(triangle1, triangle2, triangle3).filter { it.isValid() }.count()
    }
    println(valid)

}