package com.shopify.adventofcode.day11

/**
 * Created by cavedon on 2017-06-03.
 */
fun main(args: Array<String>) {

    val floors = listOf(Floor(0), Floor(1), Floor(2), Floor(3))

//    floors[0].chips.addAll(listOf(Microchip("thulium"), Microchip("ruthenium"), Microchip("cobalt")))
//    floors[0].generators.addAll(listOf(Generator("polonium"), Generator("thulium"), Generator("promethium"), Generator("ruthenium"), Generator("cobalt")))
//    floors[1].chips.addAll(listOf(Microchip("polonium"), Microchip("promethium")))

    floors[0].chips.addAll(listOf(Microchip("hydrogen"), Microchip("lithium")))
    floors[1].generators.add(Generator("hydrogen"))
    floors[2].generators.add(Generator("lithium"))

    val elevator = Elevator(floors)

    elevator.move()
    floors.map { println(it) }


}

class Elevator(val floors: List<Floor>) {

    var trips: Int = 0
    var currentFloor = floors[0]
    val chips = ArrayList<Microchip>(2)
    val generators = ArrayList<Generator>(1)

    fun up() {
        trips++
        currentFloor = floors[floors.indexOf(currentFloor) + 1]
    }

    fun down() {
        trips++
        currentFloor = floors[floors.indexOf(currentFloor) - 1]
    }

    fun move() {
        val matchingSet = currentFloor.matchingSet()
        if (matchingSet != null) {
            chips.add(matchingSet.first)
            generators.add(matchingSet.second)

        }

        if (currentFloor.id == 0) {

            up()
        }
        if (currentFloor.id == 3) down()

        recharge()
    }

    fun recharge() {
        currentFloor.chips.addAll(chips)
        currentFloor.generators.addAll(generators)
        chips.clear()
        generators.clear()
    }

}

data class Microchip(val type: String) {
    override fun toString(): String {
        return "${type.first()}M".toUpperCase()
    }
}

data class Generator(val type: String) {
    override fun toString(): String {
        return "${type.first()}M".toUpperCase()
    }
}

data class Floor(val id: Int) {
    val chips = ArrayList<Microchip>()
    val generators = ArrayList<Generator>()

    override fun toString(): String {
        return "Floor(id=$id, chips=$chips, generators=$generators"
    }

    fun matchingSet(): Pair<Microchip, Generator>? {
        chips.forEach { c ->
            generators.forEach { g ->
                if (c.type.first() == g.type.first()) {
                    return Pair(c, g)
                }
            }
        }
        return null
    }
}