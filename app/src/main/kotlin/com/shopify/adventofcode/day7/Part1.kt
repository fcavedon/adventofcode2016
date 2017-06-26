package com.shopify.adventofcode.day7

import com.shopify.adventofcode.isEven

/**
 * Created by cavedon on 2017-06-25.
 */
fun main(args: Array<String>) {

    println(input.count { line ->
        val split = line.split("[", "]")

        val (supernet, hypernet) = split.mapIndexed { index, s -> Pair(index, s) }.partition { it.first.isEven() }

        val validSupernet = supernet.map { isAbba(it.second) }.any { it }
        val validHypernet = hypernet.map { isAbba(it.second) }.none { it }

        validHypernet && validSupernet
    })
}

fun isAbba(input: String): Boolean {
    with(input) {
        return (4 until length + 1).withIndex()
                .map { substring(it.index, it.value).isPalindrome() }
                .any { it }
    }
}

fun String.isPalindrome(): Boolean = this[0] == this[3] && this[1] == this[2] && this[0] != this[1]