package com.shopify.adventofcode

import java.security.MessageDigest

/**
 * Created by cavedon on 2017-06-22.
 *
 * Common functions and classes used across puzzles
 */
const val ALPHA_CHARS = "abcdefghijklmnopqrstuvwxyz"

val HEX_CHARS = "0123456789abcdef".toCharArray()

fun Int.isEven(): Boolean = this % 2 == 0

fun Int.toBinaryString(): String = Integer.toBinaryString(this)
fun Int.pow(exp: Int): Int = Math.pow(this.toDouble(), exp.toDouble()).toInt()

fun String.asInt(): Int = trim().toInt()
fun Char.asInt(): Int = toString().toInt()


/**
 * Returns the md5 hash of a given string.
 *
 * @param times the number of iterations to apply
 * @return hash of the given string
 */
fun String.md5(times: Int = 1): String {
    val digest = MessageDigest.getInstance("MD5")
    digest.update(this.toByteArray())

    var stretched: String = ""

    kotlin.repeat(times) {
        stretched = digest.digest().toHexString()
        digest.update(stretched.toByteArray())
    }

    return stretched
}

/**
 * Converts this byte into hex representation
 */
fun Byte.toHexString(): String {
    val i = toInt()
    val (char1, char2) = Pair(HEX_CHARS[i shr 4 and 0x0f], HEX_CHARS[i and 0x0f])
    return "$char1$char2"
}

/**
 * Converts this byte array into hex representation, concatenating each
 * byte' hex representation
 */
fun ByteArray.toHexString(): String = buildString {
    this@toHexString.map { append(it.toHexString()) }
}

open class Point(open var x: Int = 0, open var y: Int = 0)

fun List<Point>.findLeft(target: Point): Point? = find { it.x == target.x - 1 && it.y == target.y }
fun List<Point>.findRight(target: Point): Point? = find { it.x == target.x + 1 && it.y == target.y }
fun List<Point>.findUp(target: Point): Point? = find { it.y == target.y + 1 && it.x == target.x }
fun List<Point>.findDown(target: Point): Point? = find { it.y == target.y - 1 && it.x == target.x }