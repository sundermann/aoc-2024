
fun main() {

    fun part1(input: List<String>): Int {
        val grids = mutableListOf<List<List<Int>>>()
        var inp = input.toMutableList()
        while (inp.isNotEmpty() && inp.first().isNotEmpty()) {
            grids += inp.takeWhile { it.isNotEmpty() }.map { it.map { if (it == '#') 1 else 0 } }
            inp = inp.dropWhile { it.isNotEmpty() }.drop(1).toMutableList()
        }

        val locks = grids.filter { it.first().all { it == 1 } }
        val keys = grids.filter { it.last().all { it == 1 } }

        return locks.sumOf { lock ->
            keys.count { key ->
                lock.zip(key).map { (l, k) ->  l.zip(k).map { (l, k) -> l + k } }.all { it.all { it <= 1 } }
            }
        }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val test = readInput("Day25_test")
    check(part1(test) == 3)
    //check(part2(test) == "co,de,ka,ta")

    val input = readInput("Day25")
    part1(input).println()
    part2(input).println()
}
