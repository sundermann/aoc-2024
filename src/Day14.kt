fun main() {
    data class Robot(val x: Int, val y: Int, val vx: Int, val vy: Int) {
        fun predict(steps: Int, width: Int, height: Int): Robot {
            return Robot((x + steps * vx).mod(width), (y + steps * vy).mod(height), vx, vy)
        }
    }

    fun String.toRobot(): Robot {
        val (x, y, vx, vy) = split(" ").flatMap { it.substringAfter('=').split(",").map { it.toInt() } }
        return Robot(x, y, vx, vy)
    }

    fun part1(input: List<String>, width: Int, height: Int): Int {
        val robots = input.map { it.toRobot().predict(100, width, height) }

        val q1 = robots.count { it.x < width / 2 && it.y < height / 2 }
        val q2 = robots.count { it.x > width / 2 && it.y < height / 2 }
        val q3 = robots.count { it.x < width / 2 && it.y > height / 2 }
        val q4 = robots.count { it.x > width / 2 && it.y > height / 2 }

        return q1 * q2 * q3 * q4
    }

    fun part2(input: List<String>, width: Int, height: Int): Int {
        val robots = input.map { it.toRobot()}

        return generateSequence(robots) { it.map { robot -> robot.predict(1, width, height) } }
            .map { it.map { robot -> robot.x to robot.y }.toSet() }
            .map {
                (0 ..< height).map { y ->
                    (0 ..< width).map { x ->
                        if (x to y in it) '.' else ' '
                    }.joinToString("")
                }
            }
            .indexOfFirst { it.any { "............." in it } }
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput, 11, 7) == 12)

    val input = readInput("Day14")
    part1(input, 101, 103).println()
    part2(input, 101, 103).println()
}
