package com.shopify.adventofcode.day8

/**
 * Created by cavedon on 2017-06-01.
 */
fun main(args: Array<String>) {

    val screen = Screen()

    for (line in input) {
        if (line.contains("rect")) {
            val cmd = line.split("rect", "x")
            screen.rect(cmd[1].trim().toInt(), cmd[2].trim().toInt())
        }

        if (line.contains("rotate row")) {
            val cmd = line.split("y=", "by")
            screen.rotate(Component.ROW, cmd[1].trim().toInt(), cmd[2].trim().toInt())
        }
        if (line.contains("rotate column")) {
            val cmd = line.split("x=", "by")
            screen.rotate(Component.COLUMN, cmd[1].trim().toInt(), cmd[2].trim().toInt())

        }
    }
    screen.print()
    println()
    println(screen.pixels.count { it.on })

}

data class Pixel(var x: Int, var y: Int, var on: Boolean = false)

class Screen(val rows: Int = 2, val columns: Int = 6) {
    var pixels = ArrayList<Pixel>()

    init {
        for (row in 0..rows) {
            for (column in 0..columns) {
                pixels.add(Pixel(row, column))
            }
        }
    }

    fun rect(x: Int, y: Int) {
        pixels.filter {
            it.y in 0 until x && it.x in 0 until y

        }.map { it.on = true }
    }

    fun rotate(component: Component, target: Int, amount: Int) {
        when (component) {
            Component.COLUMN -> {
                pixels.filter { it.y == target }.map { p ->
                    if (p.x + amount > rows) {
                        p.x = ((p.x + amount) % rows) - 1
                    } else {
                        p.x += amount
                    }
                }

            }
            Component.ROW -> {
                pixels.filter { it.x == target }.map { p ->
                    if (p.y + amount > columns) {
                        p.y = ((p.y + amount) % columns) - 1
                    } else {
                        p.y += amount
                    }
                }
            }
        }
    }

    fun print() {
        for (i in 0..rows) {
            val row = pixels.filter { it.x == i }
            row.forEach {
                if (it.on) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}

enum class Component {
    ROW, COLUMN
}