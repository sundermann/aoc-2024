import java.util.*

fun main() {

    data class Point(val x: Int, val y: Int) {
        fun move(direction: Direction) = when (direction) {
            Direction.UP -> copy(x = x - 1)
            Direction.RIGHT -> copy(y = y + 1)
            Direction.DOWN -> copy(x = x + 1)
            Direction.LEFT -> copy(y = y - 1)
        }
    }
    data class State(val point: Point, val direction: Direction, val cost: Int)
    data class Path(val state: State, val path: List<State>)

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
        val visited = mutableSetOf<Pair<Point, Direction>>()

        queue.add(State(start, Direction.UP, 0))
        while (queue.isNotEmpty()) {
            val (point, direction, cost) = queue.remove()
            visited += point to direction

            if (point == end)
                return cost

            queue += listOf(
                State(point, direction.rotateLeft(), cost + 1000),
                State(point, direction.rotateRight(), cost + 1000),
                State(point.move(direction), direction, cost + 1)
            ).filter { it.point !in obstacles && it.point to it.direction !in visited }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val (start, end, obstacles) = input.parse()
        val queue = PriorityQueue<Path>(compareBy { it.state.cost })
        val visited = mutableSetOf<Pair<Point, Direction>>()

        queue.add(Path(State(start, Direction.RIGHT, 0), emptyList()))
        var bestCost = Int.MAX_VALUE
        val pathSpots = mutableSetOf(end)
        while (queue.isNotEmpty()) {
            val (state, path) = queue.remove()
            val (point, direction, cost) = state
            visited += state.point to state.direction

            if (point == end) {
                if (cost <= bestCost) {
                    bestCost = cost
                    pathSpots += path.map { it.point }
                } else {
                    break
                }
            }

            queue += listOf(
                Path(State(point, direction.rotateLeft(), cost + 1000), path + state),
                Path(State(point, direction.rotateRight(), cost + 1000), path + state),
                Path(State(point.move(direction), direction, cost + 1), path + state)
            ).filter { it.state.point !in obstacles && it.state.point to it.state.direction !in visited }
        }

        return pathSpots.size
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 11048)
    check(part2(testInput) == 64)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
