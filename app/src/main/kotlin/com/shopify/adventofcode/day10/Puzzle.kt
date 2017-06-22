package com.shopify.adventofcode.day10

import com.shopify.adventofcode.asInt
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.set

/**
 * Created by cavedon on 2017-06-02.
 */
fun main(args: Array<String>) {

    val bins = HashMap<Int, ArrayList<Int>>()
    val bots = HashMap<Int, Bot>()

    // Initialization code
    for (line in input) {
        if (line.contains("goes to bot")) {
            // Get bot id's from this line
            val init = line.split("value", "goes to bot").filter { it != "" }
            // Add new bots to the list, along with some chips
            val bot = obtainBot(init[1].asInt(), bots)
            bot.addChip(init[0].asInt())

        } else {
            // Add new bots to the list
            val split = line.split("bot ", "gives ", "to ", "and ", "to ").filter { it != "" }
            val sender = obtainBot(split[0].asInt(), bots)

            // Add that bots' instruction set
            with(sender.instructions) {
                add(Instruction(split[2], split[1]))
                add(Instruction(split[4], split[3]))
            }
        }
    }

    // Iterate over all bots' instruction set
    while (bots.instructionCount() > 0) {
        bots.values.forEach { bot ->
            // For each bot, we only perform an instruction if it has 2 chips
            if (bot.chips.size == 2) {
                // Remove those instructions and perform them
                listOf(bot.instructions.remove(), bot.instructions.remove()).forEach { instruction ->
                    val chip = { if (instruction.exchangeLow()) bot.low() else bot.high() }

                    when (instruction.discardChip()) {
                    // Discard chips into bin
                        true -> {
                            val bin = obtainBin(instruction.discardBinId(), bins)
                            chip()?.let { bin.add(it) }
                        }
                    // Transfer chips between robots
                        else -> {
                            val targetBot = obtainBot(instruction.targetBotId(), bots)
                            chip()?.let { targetBot.addChip(it) }
                        }
                    }
                }
            }
        }
    }

    // Part2
    println(bins.values.take(3).fold(1, { acc, l -> l.first() * acc }))

}

/**
 * Returns how many instructions this collection' bots have
 *
 * @return sum of all this collections' bots' instructions
 */
fun HashMap<Int, Bot>.instructionCount(): Int = this.values.fold(0, { acc, bot -> acc + bot.instructions.size })

/**
 * Given an id, returns the specified bot from the map;
 * if no bot is found, create a new one, add to the map
 * and return it
 *
 * @param id the bot id
 * @param bots Map of bots, keyed by id
 *
 * @return a bot from the map (either a new or existing one)
 */
fun obtainBot(id: Int, bots: HashMap<Int, Bot>): Bot {
    var bot = bots[id]

    if (bot == null) {
        bot = Bot(id)
        bots[id] = bot
    }
    return bot
}

/**
 * Given an id, returns the specified bin from the map;
 * if no bin is found, create a new one, add to the map
 * and return it
 *
 * @param id the bin id
 * @param bins Map of bins, keyed by id
 *
 * @return a bin from the map (either a new or existing one)
 */
fun obtainBin(id: Int, bins: HashMap<Int, ArrayList<Int>>): ArrayList<Int> {
    var bin = bins[id]

    if (bin == null) {
        bin = ArrayList()
        bins[id] = bin
    }
    return bin
}

data class Bot(var id: Int) {
    val chips = HashSet<Int>()
    val instructions = LinkedList<Instruction>()

    fun addChip(chip: Int?) {
        if (chip != null) {
            chips.add(chip)
        }
        //Part1
        if (chips.contains(17) && chips.contains(61)) {
            println("Bot $id is responsible for the chips")
        }
    }

    /**
     * Removes the lowest-value chip from the robot, or null if none present
     *
     * @return the lowest-value chip
     */
    fun low(): Int? {
        val min = chips.min() ?: return null
        chips.remove(min)

        return min
    }

    /**
     * Removes the highest-value chip from the robot, or null if none present
     *
     * @return the highest-value chip
     */
    fun high(): Int? {
        val max = chips.max() ?: return null
        chips.remove(max)

        return max
    }
}

data class Instruction(val target: String, val chip: String) {

    fun exchangeLow(): Boolean = chip == "low "
    fun discardChip(): Boolean = target.contains("output")

    fun targetBotId(): Int = target.trim().toInt()
    fun discardBinId(): Int = target.split("output ")[1].asInt()
}