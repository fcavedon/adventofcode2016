package com.shopify.adventofcode.day14

import com.shopify.adventofcode.md5

/**
 * Created by cavedon on 2017-06-08.
 */

val HASHES = HashMap<Int, String>()

fun main(args: Array<String>) {
    val indices = ArrayList<Int>()

    var idx = 0

    while (indices.size != 64) {
        if (isKey(input, idx)) {
            indices.add(idx)
        }
        idx++
    }
    println(indices.last())
}

fun String.keyCandidate(): Char? {
    val candidateMap = HashMap<Int, Char>()

    for (c in this) {
        if (("$c$c$c") in this) {
            candidateMap[this.indexOf("$c$c$c")] = c
        }
    }

    if (candidateMap.isEmpty()) return null

    return candidateMap[candidateMap.keys.min()]
}

fun isKey(input: String, idx: Int, iterations: Int = 1): Boolean {
    if (HASHES[idx] == null) {
        HASHES[idx] = "$input$idx".md5(iterations)
    }

    val char = HASHES[idx]!!.keyCandidate() ?: return false

    return generateSequence(idx + 1) { it + 1 }
            .take(1000)
            .map {
                if (HASHES[it] == null) {
                    HASHES[it] = "$input$it".md5(iterations)
                }

                HASHES[it]!!.contains("$char$char$char$char$char")
            }
            .find { it } ?: false
}

