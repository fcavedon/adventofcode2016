package com.shopify.adventofcode.day12

/**
 * Created by cavedon on 2017-06-03.
 */
fun main(args: Array<String>) {

    val instructions = ArrayList<Instruction>()
    val registers = listOf(
            Register("a", 0),
            Register("b", 0),
            Register("c", 0), // Comment for Part 2
            //Register("c", 1), Uncomment for Part 2
            Register("d", 0)
    )

    // Init instruction set
    for (line in input) {
        val split = line.split(" ")

        with(line) {
            if (startsWith("cpy")) {
                instructions.add(CopyInstruction(split[1], split[2]))
            }
            if (startsWith("jnz")) {
                instructions.add(JumpInstruction(split[1], split[2]))
            }
            if (startsWith("inc")) {
                instructions.add(IncInstruction(split[1]))
            }
            if (startsWith("dec")) {
                instructions.add(DecInstruction(split[1]))
            }
        }
    }

    // Program counter
    var ptr: Int = 0
    var next = instructions[ptr]

    while (true) {
        next.exec(registers)
        ptr = next.next(ptr)

        if (ptr < 0 || ptr >= instructions.size) break
        next = instructions[ptr]
    }

    println(registers.first().value)

}


data class Register(val id: String, var value: Int)

interface Instruction {
    /**
     * Execute this instruction, potentially changing the program counter
     */
    fun exec(registers: List<Register>)

    /**
     * Retrieve the next instruction's index
     */
    fun next(currPointer: Int): Int
}

/**
 * Copies [value] (either an integer or the value of a register) into register [dest].
 */
data class CopyInstruction(val value: String, val dest: String) : Instruction {

    override fun exec(registers: List<Register>) {
        val destReg = registers.find { it.id == dest }
        val sourceReg = registers.find { it.id == value }

        // If dest is a register, use source.value, otherwise use value as an Int
        if (sourceReg != null) {
            destReg?.value = sourceReg.value
        } else {
            destReg?.value = value.toInt()
        }
    }

    override fun next(currPointer: Int): Int {
        // This instruction does not affect the program counter
        return currPointer + 1
    }
}

/**
 * Increases the value of register [target] by one.
 */
data class IncInstruction(val target: String) : Instruction {

    override fun exec(registers: List<Register>) {
        registers.find { it.id == target }!!.value++
    }

    override fun next(currPointer: Int): Int {
        // This instruction does not affect the program counter
        return currPointer + 1
    }
}

/**
 * Decreases the value of register [target] by one.
 */
data class DecInstruction(val target: String) : Instruction {

    override fun exec(registers: List<Register>) {
        registers.find { it.id == target }!!.value--
    }

    override fun next(currPointer: Int): Int {
        // This instruction does not affect the program counter
        return currPointer + 1
    }
}

/**
 * Jumps to an instruction [target] away (positive means forward; negative means backward),
 * but only if [base] is not zero.
 */
data class JumpInstruction(val base: String, val target: String) : Instruction {

    var origin: Register? = null
    override fun exec(registers: List<Register>) {
        // We may use regular numbers as base; this would create an unconditional jump
        // as long as base != 0
        if (listOf("a", "b", "c", "d").contains(base)) {
            origin = registers.find { it.id == base }
        }
    }

    override fun next(currPointer: Int): Int {
        // If base.value is zero, don' jump
        origin?.let { if (it.value == 0) return currPointer + 1 }

        // Jump to the given offset
        return currPointer + target.toInt()
    }
}