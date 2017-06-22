package com.shopify.adventofcode.day6

/**
 * Created by cavedon on 2017-06-19.
 */
fun main(args: Array<String>) {

    val occurrences = HashMap<Int, ArrayList<Char>>()

    input.map { line ->
        (0..7).forEach {
            if (occurrences[it] == null) {
                occurrences[it] = ArrayList<Char>()
            }
            occurrences[it]?.add(line[it])
        }
    }

    // Part1
    println(occurrences.map { it.value.groupingBy { it }.eachCount().maxBy { it.value }?.key }.joinToString(""))

    // Part2
    println(occurrences.map { it.value.groupingBy { it }.eachCount().minBy { it.value }?.key }.joinToString(""))

}