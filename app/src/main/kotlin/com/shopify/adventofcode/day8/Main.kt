package com.shopify.adventofcode.day8

import com.shopify.adventofcode.Point
import com.shopify.adventofcode.asInt

/**
 * Created by cavedon on 2017-06-01.
 */
fun main(args: Array<String>) {

    val screen = Screen(5, 49)

    for (line in input) {
        if (line.contains("rect")) {
            val cmd = line.split("rect", "x")
            screen.rect(cmd[1].asInt(), cmd[2].asInt())
        }

        if (line.contains("row")) {
            val cmd = line.split("y=", "by")
            screen.rotate(Component.ROW, cmd[1].asInt(), cmd[2].asInt())
        }
        if (line.contains("column")) {
            val cmd = line.split("x=", "by")
            screen.rotate(Component.COLUMN, cmd[1].asInt(), cmd[2].asInt())

        }
    }
    screen.print()
    println(screen.pixels.filter { it.on }.count())
}

data class Pixel(override var x: Int, override var y: Int, var on: Boolean = false) : Point(x, y) {
    override fun toString(): String {
        if (on) return "#"

        return " "
    }
}

class Screen(val rows: Int = 2, val columns: Int = 6) {
    var pixels = ArrayList<Pixel>()

    init {
        for (row in 0..rows) {
            for (column in 0..columns) {
                pixels.add(Pixel(column, row))
            }
        }
    }

    fun rect(x: Int, y: Int) {
        pixels.filter { it.x < x && it.y < y }.map { it.on = true }
    }

    fun rotate(component: Component, target: Int, amount: Int) {
        when (component) {
            Component.COLUMN -> {
                pixels.filter { it.x == target }.map { p ->
                    p.y = (p.y + amount) % (rows + 1)
                }
            }

            Component.ROW -> {
                pixels.filter { it.y == target }.map { p ->
                    p.x = (p.x + amount) % (columns + 1)
                }
            }
        }
    }

    fun print() {
        for (i in 0..rows) {
            pixels.filter { it.y == i }.sortedBy { it.x }.forEachIndexed { index, pixel ->
                print(pixel)
                if (index + 1 % 5 == 0) print("  ")
            }
            println()
        }
    }

}

enum class Component {
    ROW, COLUMN
}