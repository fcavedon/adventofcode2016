package com.shopify.adventofcode.day14

/**
 * Created by cavedon on 2017-06-22.
 */
fun main(args: Array<String>) {
    val indices = ArrayList<Int>()

    var idx = 0

    while (indices.size != 64) {
        if (isKey(input, idx, 2017)) {
            indices.add(idx)
        }
        idx++
    }
    println(indices.last())
}