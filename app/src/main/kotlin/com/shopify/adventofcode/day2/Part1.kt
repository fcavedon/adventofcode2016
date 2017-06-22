package com.shopify.adventofcode.day2

import com.shopify.adventofcode.*

/**
 * Created by cavedon on 2017-06-20.
 */
fun main(args: Array<String>) {
    val keypad = listOf(
            Key(0, 2, '1'),
            Key(1, 2, '2'),
            Key(2, 2, '3'),
            Key(0, 1, '4'),
            Key(1, 1, '5'),
            Key(2, 1, '6'),
            Key(0, 0, '7'),
            Key(1, 0, '8'),
            Key(2, 0, '9'))

    var current = Key()

    println(buildString {
        input.forEach { line ->
            for (c in line) {
                current = current.move(c, keypad)
            }
            append(current.value)
        }
    })
}

data class Key(override var x: Int = 1, override var y: Int = 1, var value: Char = '5') : Point() {

    fun move(input: Char, keypad: List<Key>): Key {
        return when (input) {
            'L' -> keypad.findLeft(this) ?: this
            'R' -> keypad.findRight(this) ?: this
            'U' -> keypad.findUp(this) ?: this
            'D' -> keypad.findDown(this) ?: this
            else -> this
        } as Key
    }
}