package com.shopify.adventofcode.day13

import com.shopify.adventofcode.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by cavedon on 2017-06-06.
 */
val ROWS = 50
val COLS = 50

fun main(args: Array<String>) {

    val points = ArrayList<Vertex>()

    // Initialize the maze
    (0..ROWS).map { row ->
        (0..COLS).mapTo(points) { col -> Vertex(row, col) }
    }

    // Add connections to each vertex
    points.forEach { v ->
        val left = points.findLeft(v)?.let { it as Vertex }
        val right = points.findRight(v)?.let { it as Vertex }
        val top = points.findUp(v)?.let { it as Vertex }
        val bottom = points.findDown(v)?.let { it as Vertex }

        listOf(left, right, top, bottom).filter { it != null && it.open }.mapTo(v.edges) { Edge(it!!) }
    }

    println(distance(points, Vertex(1, 1), Vertex(31, 39)))
}

/**
 * Dijkstra's algorithm for the distance between [source] and [target]
 *
 * @see (https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
 */
fun distance(vertices: List<Vertex>, source: Vertex, target: Vertex): Int {
    val unvisited = LinkedList<Vertex>()

    vertices.find { it == source }?.targetDist = 0
    unvisited.addAll(vertices)

    while (unvisited.isNotEmpty()) {
        val current = unvisited.minBy { it.targetDist }!!
        unvisited.remove(current)

        if (current == target) break

        current.edges.forEach { (dest, dist) ->
            val sum = current.targetDist + dist

            if (sum < dest.targetDist) {
                dest.targetDist = sum
            }
        }
    }

    // Part 2
    println(vertices.sortedBy { it.targetDist }.filter { it.targetDist < 51 }.count())

    return vertices.sortedBy { it.targetDist }.filter { it.targetDist < 9999 }.map { it.targetDist }.last()
}

data class Vertex(override var x: Int, override var y: Int, var targetDist: Int = 9999) : Point() {
    val edges = ArrayList<Edge>()

    // Puzzle's rule
    val open: Boolean by lazy {
        val equation = (((x + y).pow(2)) + (3.times(x) + y)) + input
        equation.toBinaryString().count { it == '1' }.isEven()
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    // We don' care about the distance; two vertexes are the same if their coords are the same
    override fun equals(other: Any?): Boolean {
        if (other !is Vertex) return false
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

/**
 * A connection between two vertexes
 */
data class Edge(val dest: Vertex, val dist: Int = 1)