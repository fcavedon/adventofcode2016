package com.shopify.adventofcode.day2

/**
 * Created by cavedon on 2017-06-20.
 */
fun main(args: Array<String>) {
    val keypad = listOf(
            Key(2, 4, '1'),
            Key(1, 3, '2'),
            Key(2, 3, '3'),
            Key(3, 3, '4'),
            Key(0, 2, '5'),
            Key(1, 2, '6'),
            Key(2, 2, '7'),
            Key(3, 2, '8'),
            Key(4, 2, '9'),
            Key(1, 1, 'A'),
            Key(2, 1, 'B'),
            Key(3, 1, 'C'),
            Key(2, 0, 'D'))

    var current = Key(0, 2, '5')

    println(buildString {
        input.forEach { line ->
            for (c in line) {
                current = current.move(c, keypad)
            }
            append(current.value)
        }
    })
}