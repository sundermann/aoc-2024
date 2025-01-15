fun main() {
    fun String.countPatterns(patterns: List<String>, cache: MutableMap<String, Long> = mutableMapOf()): Long {
        return cache.getOrPut(this) {
            patterns.count { it == this } +
            patterns.filter { this.startsWith(it) }
                .map { this.drop(it.length) }
                .sumOf { it.countPatterns(patterns, cache) }
        }
    }

    fun part1(input: List<String>): Int {
        val patterns = input.first().split(", ")
        val designs = input.drop(2)
        return designs.count { it.countPatterns(patterns) > 0 }
    }

    fun part2(input: List<String>): Long {
        val patterns = input.first().split(", ")
        val designs = input.drop(2)
        return designs.sumOf { it.countPatterns(patterns) }
    }

    val testInput = readInput("Day19_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
