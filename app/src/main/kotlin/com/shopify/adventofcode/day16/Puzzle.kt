package com.shopify.adventofcode.day16

/**
 * Created by cavedon on 2017-06-23.
 */
fun main(args: Array<String>) {

    val part1 = 272
    val part2 = 35651584

    val data = generateData(input, part1)
    println(checksum(data.substring(0, part1)))

}

fun generateData(input: String, size: Int = 1): String {
    val b = input.reversed().map { if (it == '1') '0' else '1' }.joinToString("")

    val result = "${input}0$b"

    return when (result.length < size) {
        true -> generateData(result, size)
        false -> result
    }
}

fun checksum(input: String): String {
    val pairs = ArrayList<Pair<Char, Char>>()

    var start = 0
    var end = 1

    while (end < input.length) {
        val pair = Pair(input[start], input[end])
        pairs.add(pair)
        start += 2
        end += 2
    }

    val result = buildString {
        pairs.map { if (it.first == it.second) "1" else "0" }.forEach { append(it) }
    }

    return when (result.length % 2) {
        0 -> checksum(result)
        else -> result
    }
}