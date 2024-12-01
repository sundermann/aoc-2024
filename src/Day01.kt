import kotlin.math.absoluteValue

fun main() {
    fun String.parse() = this.split("   ")
        .map { it.toInt() }.let { it[0] to it[1] }

    fun part1(input: List<String>): Int {
        val (fst, snd) = input
            .map { it.parse() }
            .unzip()

        return fst.sorted()
            .zip(snd.sorted())
            .sumOf { (a, b) -> (a - b).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        val (fst, snd) = input
            .map { it.parse() }
            .unzip()

        val freq = snd.groupingBy { it }.eachCount()

        return fst.sumOf { it * (freq[it] ?: 0) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
