package com.shopify.adventofcode.day1

/**
 * Created by cavedon on 2017-06-07.
 */
fun main(args: Array<String>) {
    var coord = CardinalPoint()

    for (cmd in input.replace(" ", "").split(',')) {
        coord = coord.move(cmd)
    }

    println(coord)
}

enum class Directions {
    N, E, S, W;

    val leftMoves: Map<Directions, Directions>
        get() = mapOf(N to W, W to S, S to E, E to N)

    val rightMoves: Map<Directions, Directions>
        get() = mapOf(N to E, E to S, S to W, W to N)

    fun rotate(to: Char): Directions {
        return when (to) {
            'L' -> leftMoves[this]!!
            'R' -> rightMoves[this]!!
            else -> this
        }
    }
}

data class CardinalPoint(var x: Int = 0, var y: Int = 0, var dir: Directions = Directions.N) {

    fun move(cmd: String): CardinalPoint {
        dir = dir.rotate(cmd[0])
        val amt = cmd.subSequence(1, cmd.length).toString().toInt()

        when (dir) {
            Directions.N -> {
                y += amt
            }
            Directions.S -> {
                y -= amt
            }
            Directions.E -> {
                x += amt
            }
            Directions.W -> {
                x -= amt
            }
        }
        return this
    }

    fun dist(): Int = (Math.abs(0 - x) + (Math.abs(0 - y)))
}