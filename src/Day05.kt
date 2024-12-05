fun main() {
    data class Rule(val before: Int, val after: Int)
    data class Update(val updates: List<Int>) {
        fun check(rules: List<Rule>) = updates.sortedWith(compare(rules)) == updates
        fun fix(rules: List<Rule>): Update = Update(this.updates.sortedWith(compare(rules)))

        fun compare(rules: List<Rule>): Comparator<Int> {
            val ordering = rules.flatMap { (before, after) ->
                listOf(after to before to 1, before to after to -1)
            }.toMap()

            return Comparator { a, b ->
                ordering[a to b] ?: 0
            }
        }
    }

    fun String.toRule() : Rule {
        val (before, after) = this.split("|")
        return Rule(before.toInt(), after.toInt())
    }

    fun String.toUpdate() = Update(this.split(",").map { it.toInt() })

    fun List<String>.splitRuleUpdate(): Pair<List<Rule>, List<Update>> {
        val split = this.indexOf("")
        return Pair(this.take(split).map { it.toRule() }, drop(split + 1).map { it.toUpdate() })
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = input.splitRuleUpdate()
        return updates
            .filter { it.check(rules) }
            .sumOf { it.updates[it.updates.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = input.splitRuleUpdate()
        return updates.filterNot { it.check(rules) }
            .map { it.fix(rules) }
            .sumOf { it.updates[it.updates.size / 2] }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
