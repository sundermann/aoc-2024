import kotlin.math.max

fun main() {

    data class Vec2D(val x: Int, val y: Int) {
        operator fun plus(other: Vec2D) = Vec2D(x + other.x, y + other.y)
        operator fun minus(other: Vec2D) = Vec2D(x - other.x, y - other.y)
        operator fun times(other: Int) = Vec2D(x * other, y * other)
    }

    fun <T> List<T>.permutedPairs(): List<Pair<T, T>> {
        val result = mutableListOf<Pair<T, T>>()
        for (i in this.indices) {
            for (j in i + 1 until this.size) {
                result.add(this[i] to this[j])
            }
        }
        return result
    }

    fun Pair<Vec2D, Vec2D>.tFrequency(min: Int, max: Int) = (min..max)
        .flatMap { listOf(first + (first - second) * it, second + (second - first) * it) }

    fun solve(input: List<String>, f: (Pair<Vec2D, Vec2D>) -> List<Vec2D>): Int {
        return input.flatMapIndexed { x, line -> line.mapIndexed { y, char -> char to Vec2D(x, y) } }
            .filter { it.first != '.' }
            .groupBy ({ it.first }, { it.second })
            .values.flatMap { pos -> pos.permutedPairs().flatMap { f(it) } }
            .filter { (x, y) -> x in input.indices && y in input[0].indices }
            .distinct()
            .count()
    }

    fun part1(input: List<String>): Int {
        return solve(input) { it.tFrequency(1, 1) }
    }

    fun part2(input: List<String>): Int {
        return solve(input) { it.tFrequency(0, max(input.count(), input[0].count())) }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
