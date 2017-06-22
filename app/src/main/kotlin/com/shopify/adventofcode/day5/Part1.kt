package com.shopify.adventofcode.day5

import com.shopify.adventofcode.md5
import java.math.BigInteger

/**
 * Created by cavedon on 2017-06-19.
 */

fun main(array: Array<String>) {

    var idx: BigInteger = BigInteger.ZERO

    val pwd = buildString {
        while (length != 8) {
            val hash = "$input$idx".md5()
            if (hash.startsWith("00000")) {
                append(hash[5])
            }
            idx += BigInteger.ONE
        }
    }

    println(pwd)

}


