package com.shopify.adventofcode.day1

/**
 * Created by cavedon on 2017-06-07.
 */
fun main(args: Array<String>) {
    var coord = CardinalPoint()

    for (cmd in input.split(", ")) {
        coord = coord.move(cmd)
    }

    println(coord.dist())
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

val visited = HashMap<String, Int>()

data class CardinalPoint(var x: Int = 0, var y: Int = 0, var dir: Directions = Directions.N) {

    fun move(cmd: String): CardinalPoint {
        dir = dir.rotate(cmd[0])
        val amt = cmd.subSequence(1, cmd.length).toString().toInt()

        (1..amt).forEach {
            when (dir) {
                Directions.N -> y++
                Directions.S -> y--
                Directions.E -> x++
                Directions.W -> x--
            }
            val newPos: String = "$x,$y"

            // Part2
            if (visited[newPos] != null) {
                println("$newPos is visited again, distance is ${dist()}")

            } else {
                visited[newPos] = 1
            }
        }

        return this
    }

    fun dist(): Int = (Math.abs(0 - x) + (Math.abs(0 - y)))
}