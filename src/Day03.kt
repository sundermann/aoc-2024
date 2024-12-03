fun main() {

    fun part1(input: List<String>): Int {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        return regex.findAll(input.joinToString())
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val regex = """(mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\))""".toRegex()
        return regex.findAll(input.joinToString()).fold(0 to true) { (sum, mulEnabled), match ->
            val (op, a, b) = match.destructured
            when {
                op == "do()" -> sum to true
                op == "don't()" -> sum to false
                op.startsWith("mul") && mulEnabled -> {
                    sum + a.toInt() * b.toInt() to true
                }
                else -> sum to mulEnabled
            }
        }.first
    }

    val testInput1 = readInput("Day03_test_01")
    val testInput2 = readInput("Day03_test_02")
    check(part1(testInput1) == 161)
    check(part2(testInput2) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
