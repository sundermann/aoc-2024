import java.util.*

fun main() {

    data class Point(val x: Int, val y: Int) {
        fun move(direction: Direction, amount: Int) = when (direction) {
            Direction.UP -> copy(x = x - amount)
            Direction.RIGHT -> copy(y = y + amount)
            Direction.DOWN -> copy(x = x + amount)
            Direction.LEFT -> copy(y = y - amount)
        }
    }
    data class State(val point: Point, val cost: Int)

    fun List<String>.parse(): Triple<Point, Point, MutableSet<Point>> {
        var start = Point(0, 0)
        var end = Point(0, 0)
        val obstacles = mutableSetOf<Point>()
        mapIndexed { y, line -> line.mapIndexed { x, c ->
            when(c) {
                '#' -> obstacles.add(Point(x, y))
                'S' -> start = Point(x, y)
                'E' -> end = Point(x, y)
                else -> {}
            }
        } }
        return Triple(start, end, obstacles)
    }

    fun part1(input: List<String>): Int {
        val (start, end, obstacles) = input.parse()
        val queue = PriorityQueue<State>(compareBy { it.cost })
        val visited = mutableSetOf<Point>()

        queue.add(State(start, 0))
        while (queue.isNotEmpty()) {
            val (point, cost) = queue.remove()
            visited += point

            if (point == end)
                return cost

            queue += listOf(
                State(point.move(Direction.UP, 2), cost + 1),
                State(point.move(Direction.DOWN, 2), cost + 1),
                State(point.move(Direction.LEFT, 2), cost + 1),
                State(point.move(Direction.RIGHT, 2), cost + 1)
            ).filter { it.point !in obstacles && it.point !in visited }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
