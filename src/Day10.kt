fun main() {

    data class Coordinate(val x: Int, val y: Int)
    data class Vertex(val num: Int, val coordinate: Coordinate, val edges: MutableList<Vertex> = mutableListOf()) {
        override fun toString(): String {
            return "Vertex(coordinate=$coordinate, edges=${edges.map { it.coordinate }})"
        }
    }

    fun List<List<Int>>.neighbours(coordinate: Coordinate) =
        listOf(Coordinate(coordinate.x - 1, coordinate.y),
            Coordinate(coordinate.x + 1, coordinate.y),
            Coordinate(coordinate.x, coordinate.y - 1),
            Coordinate(coordinate.x, coordinate.y + 1))
            .filter { it.x in indices && it.y in this[it.x].indices }
            .filter { this[it.x][it.y] == this[coordinate.x][coordinate.y] + 1 }

    fun solve(input: List<String>, p2: Boolean = false): Int {
        val grid = input.map { it.map { it.digitToInt() } }
        val map = input.indices.flatMap { x ->
            input[x].indices.map { y -> (Coordinate(x, y)) to Vertex(grid[x][y], Coordinate(x, y)) } }.toMap()
        map.forEach { (coordinate, vertex) ->
            grid.neighbours(coordinate).forEach { neighbour -> vertex.edges.add(map[neighbour]!!) }
        }

        val start = map.entries.filter { it.value.num == 0 }.map { it.key }
        fun dfs(current: Vertex, visited: MutableSet<Vertex> = mutableSetOf()): Int {
            if (!p2 && current in visited) return 0
            visited.add(current)
            if (current.num == 9) return 1

            return map[current.coordinate]!!.edges.sumOf { dfs(it, visited) }
        }

        return start.sumOf { dfs(map[it]!!) }
    }

    fun part1(input: List<String>): Int {
        return solve(input)
    }

    fun part2(input: List<String>): Int {
        return solve(input, true)
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
