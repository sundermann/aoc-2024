fun main() {
    fun List<Char>.move(): List<Char> {
        val free = indexOfFirst { it == '.' }
        val end = indexOfFirst { it == '#' }
        return if (free != -1 && free < end) {
            val new = toMutableList()
            new[free] = 'O'
            new[1] = '@'
            new[0] = '.'
            new
        }
        else this
    }

    fun List<Char>.widen(): List<Char> {
        return flatMap {
            when(it) {
                'O' -> listOf('[', ']')
                '@' -> listOf('@', '.')
                else -> listOf(it, it)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val grid = input.takeWhile { it.isNotEmpty() }.map { it.toMutableList() }.toMutableList()
        val instructions = input.dropWhile { it.isNotEmpty() }.drop(1).joinToString("")

        return instructions.fold(grid) { grid, instruction ->
            val y = grid.indexOfFirst { it.contains('@') }
            val x = grid[y].indexOf('@')

            when(instruction) {
                '>' -> {
                    val new = (x ..< grid[x].size).map { x -> grid[y][x] }
                    val moved = new.move()
                    (x ..< grid[x].size).forEachIndexed { i, x -> grid[y][x] = moved[i] }
                }
                '<' -> {
                    val new = (0 .. x).map { x -> grid[y][x] }.reversed()
                    val moved = new.move().reversed()
                    (0 .. x).forEachIndexed { i, x -> grid[y][x] = moved[i] }
                }
                'v' -> {
                    val new = (y ..< grid.size).map { y -> grid[y][x] }
                    val moved = new.move()
                    (y ..< grid.size).forEachIndexed { i, y -> grid[y][x] = moved[i] }
                }
                '^' -> {
                    val new = (0 .. y).map { y -> grid[y][x] }.reversed()
                    val moved = new.move().reversed()
                    (0 .. y).forEachIndexed { i, y -> grid[y][x] = moved[i] }
                }
            }

            grid
        }
            .mapIndexed { y, row -> row.withIndex().map { (x, c) -> Triple(x, y, c) } }
            .flatten()
            .filter { (x, y, c) -> c == 'O' }
            .sumOf { (x, y, c) -> x + y  * 100 }
    }

    fun part2(input: List<String>): Int {
        val grid = input.takeWhile { it.isNotEmpty() }.map { it.toMutableList() }.toMutableList().map { it.widen() }
        val instructions = input.dropWhile { it.isNotEmpty() }.drop(1).joinToString("")
        return 0
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10092)
    check(part2(testInput) == 9021)

    val testInput2 = readInput("Day15_test2")
    check(part1(testInput2) == 2028)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
