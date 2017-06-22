package com.shopify.adventofcode.day15

/**
 * Created by cavedon on 2017-06-22.
 */

/**
 * The logic for this one was done entirely on paper :D
 *
 * After X rotations, all disks will be aligned at position 0.
 *
 * Using the sample input provided:
 *
 * Disc #1 has 5 positions; at time=0, it is at position 4.
 * Disc #2 has 2 positions; at time=0, it is at position 1.
 *
 * Each disk number' add to the number of revolutions, as they all move at the same time.
 * Also, we need to consider each starting position as well.
 *
 * Because of this, each disk will complete a revolution when X satisfies the following:
 * ([X] + [disk number] + [starting position]) % [total positions] == 0
 *
 * In the sample above, we need to find a X so that:
 *
 * ((X+1+4) % 5) ==0 && ((X+2+1) % 2) == 0
 *
 * The first X that satisfies the equation is 5:
 *
 * (5+1+4) = 10 MOD 5 == 0
 * (5+2+1) = 8 MOD 2 == 0
 *
 * To solve this puzzle, we just have to find the X that satisfies all modulo equations.
 */
fun main(args: Array<String>) {
    println(generateSequence(0) { it + 1 }
            .find { x ->
                ((x + 2) % 13 == 0 &&
                        (x + 12) % 19 == 0 &&
                        (x + 5) % 3 == 0 &&
                        (x + 5) % 7 == 0 &&
                        (x + 8) % 5 == 0 &&
                        (x + 11) % 17 == 0 &&
                        (x + 7) % 11 == 0 // Part 2
                        )
            })
}
