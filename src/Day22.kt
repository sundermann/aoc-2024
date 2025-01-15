

fun main() {

    fun Long.secret(): Long {
        var secret = this
        secret = (secret * 64L xor secret) % 16777216L
        secret = ((secret / 32) xor secret) % 16777216L
        secret = ((secret * 2048) xor secret) % 16777216L
        return secret
    }

    fun Long.lastDigit(): Long {
        return this.toString().last().toString().toLong()
    }

    fun Long.secrets(): List<Long> {
        var secret = this
        val secrets = mutableListOf<Long>(secret)
        repeat(2000) {
            secret = secret.secret()
            secrets.add(secret)
        }
        return secrets
    }

    fun part1(input: List<String>): Long {
        return input.map { it.toLong() }
            .sumOf { var s = it; repeat(2000) { s = s.secret() }; s }
    }

    fun part2(input: List<String>): Long {
        val secrets = input.map { it.toLong() }
            .map { it.secrets() }
            .map { it.map { it.lastDigit() } }

        val diffs = secrets
            .map { it.windowed(2) }
            .map { it.map { it[1] - it[0] } }

        val map = mutableMapOf<List<Long>, Long>()
        diffs.forEachIndexed { j, diff ->
            val seen = mutableSetOf<List<Long>>()
            diff.windowed(4).withIndex()
                .forEach { (i, nums) -> if (seen.add(nums)) map[nums] =secrets[j][i + 4] + map.getOrDefault(nums, 0) }
        }

        return map.values.max()
    }

    val test = readInput("Day22_test")
    check(part1(test) == 37327623L)
    //check(part2(test) == 23L)


    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
