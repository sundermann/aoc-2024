fun main() {

    data class Point(val x: Int, val y: Int)
    data class State(val point: Point, val cost: Int)

    fun solve(obstacles: Set<Point>, size: Int): Int {
        val start = Point(0, 0)
        val end = Point(size, size)
        val queue = mutableListOf<State>()
        val visited = mutableSetOf<Point>()

        queue.add(State(start, 0))
        while (queue.isNotEmpty()) {
            val (point, cost) = queue.removeFirst()
            if (point == end)
                return cost

            queue += listOf(
                State(point.copy(x = point.x + 1), cost + 1),
                State(point.copy(x = point.x - 1), cost + 1),
                State(point.copy(y = point.y + 1), cost + 1),
                State(point.copy(y = point.y - 1), cost + 1),
            )
                .filter { it.point.x in 0..size && it.point.y in 0..size }
                .filter { it.point !in obstacles && it.point !in visited }
                .also { visited += it.map { it.point } }
        }

        return -1
    }

    fun part1(input: List<String>, size: Int, bytes: Int): Int {
        val obstacles = input.take(bytes).map { val (x, y) = it.split(","); Point(x.toInt(), y.toInt()) }
        return solve(obstacles.toSet(), size)
    }

    fun part2(input: List<String>, size: Int): String {
        val obstacles = input.map { val (x, y) = it.split(","); Point(x.toInt(), y.toInt()) }
        return obstacles.indices.first { solve(obstacles.take(it + 1).toSet(), size) == -1 }
            .let { "${obstacles[it].x},${obstacles[it].y}" }
    }

    val testInput = readInput("Day18_test")
    check(part1(testInput, 6, 12) == 22)
    check(part2(testInput, 6) == "6,1")

    val input = readInput("Day18")
    part1(input, 70, 1024).println()
    part2(input, 70).println()
}
