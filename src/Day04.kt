fun main() {

    data class Position(val x: Int, val y: Int)

    fun List<List<Char>>.getOrNull(position: Position) = this.getOrNull(position.x)?.getOrNull(position.y)

    fun getPositions(from: Position): List<List<Position>> {
        val range = "XMAS".indices
        return listOf(
            range.map { Position(from.x, from.y + it) }, // down
            range.map { Position(from.x + it, from.y) }, // right
            range.map { Position(from.x - it, from.y) }, // left
            range.map { Position(from.x, from.y - it) }, // up
            range.map { Position(from.x + it, from.y + it) }, // down right
            range.map { Position(from.x + it, from.y - it) }, // up right
            range.map { Position(from.x - it, from.y + it) }, // down left
            range.map { Position(from.x - it, from.y - it) }, // up left
        )
    }

    fun wordsStartingAt(grid: List<List<Char>>, position: Position) = getPositions(position)
        .sumOf {
            val words = it.mapNotNull { word -> grid.getOrNull(word) }
            if (words.joinToString("") == "XMAS") 1 as Int else 0
        }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.toList() }
        return grid.indices.flatMap { x ->
            grid[x].indices.map { y ->
                wordsStartingAt(grid, Position(x, y))
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return (0 .. input.lastIndex - "MAS".lastIndex).flatMap { i ->
            (0 .. input[0].lastIndex - "MAS".lastIndex).map { j ->
                listOf(
                    listOf(input[i][j], input[i+1][j+1], input[i+2][j+2]).joinToString(""),
                    listOf(input[i][j+2], input[i+1][j+1], input[i+2][j]).joinToString("")
                )
            }
        }.count { (it[0] == "MAS" || it[0] == "SAM" ) && (it[1] == "MAS" || it[1] == "SAM") }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
