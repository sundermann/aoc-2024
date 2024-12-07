fun main() {
    fun String.parse(): Pair<Long, List<Long>> {
        val (res, nums) = this.split(": ")
        return res.toLong() to nums.split(" ").map { it.toLong() }
    }

    fun isSolvable(res: Long, nums: List<Long>, p2: Boolean = false): Boolean {
        val x = nums.last()
        if (nums.count() == 1) return res == x
        val xs = nums.dropLast(1)
        return when {
            res % x == 0L && isSolvable(res / x, xs, p2) -> true
            res - x > 0 && isSolvable(res - x, xs, p2) -> true
            p2 && res.toString().length > x.toString().length && res.toString().endsWith(x.toString()) &&
                    isSolvable(res.toString().removeSuffix(x.toString()).toLong(), xs, true) -> true
            else -> false
        }
    }

    fun part1(input: List<String>): Long {
        return input.map { it.parse() }
            .filter { (res, nums) -> isSolvable(res, nums) }
            .sumOf { it.first }
    }

    fun part2(input: List<String>): Long {
         return input.map { it.parse() }
             .filter { (res, nums) -> isSolvable(res, nums, true) }
             .sumOf { it.first }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
