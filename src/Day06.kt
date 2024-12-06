enum class Direction { UP, RIGHT, DOWN, LEFT;
    fun rotateRight() = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }
}

fun main() {
    data class Coordinate(val x: Int, val y: Int)
    data class Pose(val position: Coordinate, val direction: Direction) {
        fun move() = when (direction) {
            Direction.UP -> copy(position = position.copy(x = position.x - 1))
            Direction.RIGHT -> copy(position = position.copy(y = position.y + 1))
            Direction.DOWN -> copy(position = position.copy(x = position.x + 1))
            Direction.LEFT -> copy(position = position.copy(y = position.y - 1))
        }
    }

    fun List<List<Char>>.getStart() = this.indexOfFirst { it.any { it == '^' } }
        .let { Pose(Coordinate(it, this[it].indexOf('^')), Direction.UP) }

    fun nextPose(grid: List<List<Char>>, pose: Pose): Pose? {
        val next = pose.move()
        val (x, y) = next.position
        if (x !in grid.indices || y !in grid.indices) return null
        if (grid[x][y] == '#')
            return Pose(pose.position, pose.direction.rotateRight())
        return next
    }

    fun List<List<Char>>.sequence(start: Pose) = generateSequence(start) { nextPose(this, it) }

    fun List<List<Char>>.isLoop(start: Pose): Boolean {
        val duplicates = mutableSetOf<Pose>()
        return sequence(start).any { !duplicates.add(it) }
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.toList() }
        val start = grid.getStart()
        val poses = grid.sequence(start).toList()
        return poses.distinctBy { it.position }.count()
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toList() }
        val start = grid.getStart()
        val poses = grid.sequence(start).toList()
        val possibleLoops = poses.map { it.position }.distinct()

        return possibleLoops.count { (x, y) ->
            val copy = grid.map { it.toMutableList() }
            copy[x][y] = '#'
            copy.isLoop(start)
        }
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
