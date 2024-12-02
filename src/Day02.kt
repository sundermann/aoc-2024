import kotlin.math.absoluteValue

fun main() {
    fun String.parse() = this.split(' ').map { it.toInt() }

    fun List<Int>.differences() = this.zipWithNext { a, b -> (a - b).absoluteValue }
    fun List<Int>.maximumDifference() = this.differences().max()
    fun List<Int>.isAscending() = this.zipWithNext().all { (a, b) -> a < b }
    fun List<Int>.isDescending() = this.zipWithNext().all { (a, b) -> a > b }
    fun List<Int>.isSafe() = maximumDifference() in 1..3 && (isAscending() || isDescending())

    fun part1(input: List<String>): Int {
        return input
            .map { it.parse() }
            .count { it.isSafe() }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.parse() }
            .map { nums -> listOf(nums) + nums.indices.map { nums.filterIndexed { i, _ -> it != i } } }
            .count { it.any { nums -> nums.isSafe() } }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
