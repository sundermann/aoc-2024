import kotlin.math.pow

data class Instruction(val operation: Int, val operand: Int) {
    val literal: Long = operand.toLong()
    fun getComboOperand(register: Register): Long {
        return when(operand) {
            0, 1, 2, 3 -> operand.toLong()
            4 -> register.a
            5 -> register.b
            6 -> register.c
            else -> error("should not appear")
        }
    }
}
data class Register(var a: Long, var b: Long, var c: Long)

fun main() {
    fun part1(input: List<String>): String {
        val (a, b, c) = input.take(3).map { it.dropWhile { !it.isDigit() }.toLong() }
        val register = Register(a, b, c)
        val instructions = input.last().dropWhile { !it.isDigit() }.split(",").windowed(2)
            .map { Instruction(it.first().toInt(), it.last().toInt()) }

        val output = mutableListOf<Long>()
        var instructionPointer = 0L
        while (instructionPointer < instructions.size) {
            val i = instructions[instructionPointer.toInt()]
            when(i.operation) {
                0 -> register.a = (register.a.toDouble() / (2.0.pow(i.getComboOperand(register).toDouble()))).toLong()
                1 -> register.b = register.b xor i.literal
                2 -> register.b = i.getComboOperand(register) % 8
                3 -> if (register.a != 0L) instructionPointer = i.literal - 2
                4 -> register.b = register.b xor register.c
                5 -> output += i.getComboOperand(register) % 8
                6 -> register.b = (register.a.toDouble() / (2.0.pow(i.getComboOperand(register).toDouble()))).toLong()
                7 -> register.c = (register.a.toDouble() / (2.0.pow(i.getComboOperand(register).toDouble()))).toLong()
            }
            instructionPointer += 2
        }
        return output.joinToString(",")
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    val testInput2 = readInput("Day17_test2")
    check(part2(testInput2) == 117440L)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
