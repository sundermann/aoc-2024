fun main() {

    fun String.splitHalf() = substring(0, length / 2).toInt().toString() to substring(length / 2).toInt().toString()

    val cache = mutableMapOf<Pair<String, Int>, Long>()
    fun blinkCount(input: String, repeat: Int): Long {
        return cache.getOrPut(input to repeat) {
            if (repeat == 0) return 1L
            when {
                input == "0" -> blinkCount("1", repeat - 1)
                input.length % 2 == 0 -> {
                    val (left, right) = input.splitHalf()
                    blinkCount(left, repeat - 1) + blinkCount(right, repeat - 1)
                }
                else -> blinkCount((input.toLong() * 2024L).toString(), repeat - 1)
            }
        }
    }

    fun part1(input: List<String>): Long {
        return input.first().split(' ')
            .sumOf { blinkCount(it, 25) }
    }

    fun part2(input: List<String>): Long {
        return input.first().split(' ')
            .sumOf { blinkCount(it, 75) }
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
