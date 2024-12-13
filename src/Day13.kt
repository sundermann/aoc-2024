fun main() {
    data class Machine(val a: Pair<Long, Long>, val b: Pair<Long, Long>, val prize: Pair<Long, Long>) {
        fun solve(): Pair<Long, Long> {
            val det = a.first * b.second - a.second * b.first
            // https://en.wikipedia.org/wiki/Cramer%27s_rule
            val ax = prize.first * b.second - prize.second * b.first
            val bx = prize.second * this.a.first - prize.first * this.a.second
            if (ax % det != 0L || bx % det != 0L) return 0L to 0L
            return ax / det to bx / det
        }
    }
    fun List<String>.toMachine(p2: Boolean = false): Machine {
        val ax = this[0].dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }.toLong()
        val ay = this[0].takeLastWhile { it.isDigit() }.toLong()
        val bx = this[1].dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }.toLong()
        val by = this[1].takeLastWhile { it.isDigit() }.toLong()
        val pa = this[2].dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }.toLong() + if (p2) 10000000000000L else 0
        val pb = this[2].takeLastWhile { it.isDigit() }.toLong() + if (p2) 10000000000000L else 0
        return Machine(ax to ay, bx to by, pa to pb)
    }

    fun part1(input: List<String>): Long {
        return input.chunked(4).map { it.toMachine() }
            .map { it.solve() }
            .sumOf { (a, b) -> a * 3 + b }
    }

    fun part2(input: List<String>): Long {
        return input.chunked(4).map { it.toMachine(true) }
            .map { it.solve() }
            .sumOf { (a, b) -> a * 3 + b }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480L)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
