

fun main() {

    fun List<String>.getConnections() = this
        .flatMap { it.split("-").let { listOf(it[0] to it[1], it[1] to it[0]) } }
        .groupBy { it.first }
        .mapValues { it.value.map { it.second }.toSet() }

    fun part1(input: List<String>): Int {
        val connections = input.getConnections()
        val seen = mutableSetOf<Set<String>>()

        for (p1 in connections.keys) {
            for (p2 in connections.getValue(p1)) {
                for (p3 in connections.getValue(p2) intersect connections.getValue(p1)) {
                    if (!p1.startsWith('t') && !p2.startsWith('t') && !p3.startsWith('t'))
                        continue
                    seen += setOf(p1, p2, p3)
                }
            }
        }

        return seen.size
    }

    fun part2(input: List<String>): String {
        val connections = input.getConnections()

        return sequence {
            val queue = connections.keys.toMutableSet()
            while (queue.isNotEmpty()) {
                val curr = queue.first()
                queue -= curr
                val connected = mutableSetOf(curr)
                connections.getValue(curr).forEach { conn ->
                    if (conn !in connected && connected.all { conn in connections.getValue(it) })
                        connected += conn
                }
                yield(connected)
            }
        }.maxBy { it.size }.sorted().joinToString(",")
    }

    val test = readInput("Day23_test")
    check(part1(test) == 7)
    check(part2(test) == "co,de,ka,ta")

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
