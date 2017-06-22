package com.shopify.adventofcode.day5

import com.shopify.adventofcode.asInt
import com.shopify.adventofcode.md5
import java.math.BigInteger

/**
 * Created by cavedon on 2017-06-19.
 */
fun main(array: Array<String>) {

    val pwd = HashMap<Int, Char>()
    var idx: BigInteger = BigInteger.ZERO

    while (pwd.size != 8) {
        val hash = "$input$idx".md5()
        if (hash.startsWith("00000") && hash[5].isDigit() && hash[5].asInt() < 8) {
            if (pwd[hash[5].asInt()] == null) {
                pwd[hash[5].asInt()] = hash[6]
            }
        }
        idx += BigInteger.ONE
    }
    println(pwd.values.joinToString(""))

}