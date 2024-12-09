sealed class Block {
    abstract val length: Int
}

data class File(override val length: Int, val id: Int) : Block()
data class Free(override val length: Int) : Block()
data class None(override val length: Int = 0) : Block()

fun main() {
    fun String.toBlocks() = chunked(2)
        .flatMapIndexed { i, p ->
            buildList {
                add(File(p[0].digitToInt(), i))
                if (p.length > 1) add(Free(p[1].digitToInt()))
            }
        }

    fun mergeToFree(file: File, free: Free): Pair<Pair<Block, Block>, Pair<Block, Block>> {
        if (file.length > free.length) {
            return (file.copy(length = free.length) to None()) to
                    (file.copy(length = file.length - free.length) to free)
        }

        if (file.length < free.length) {
            return (file to free.copy(length = free.length - file.length)) to
                    (None() to free.copy(length = file.length))
        }

        return (file to None()) to (None() to free)
    }

    fun tryMergeToFree(file: File, free: Free): Pair<Pair<Block, Block>, Block> {
        if (file.length < free.length) {
            return (file to free.copy(length = free.length - file.length)) to
                    free.copy(length = file.length)
        }

        if (file.length == free.length) {
            return (file to None()) to free
        }

        return (free to None()) to file
    }


    fun part1(input: List<String>): Long {
        val blocks = input.first().toBlocks().toMutableList()
        var freeIdx = 1
        var fileIdx = blocks.indexOfLast { it is File }
        while (freeIdx < fileIdx) {
            val (file, free) = blocks[fileIdx] to blocks[freeIdx]

            mergeToFree(file as File, free as Free).let { (left, right) ->
                blocks[freeIdx] = left.first
                blocks.add(freeIdx + 1, left.second)

                blocks[fileIdx + 1] = right.first
                blocks.add(fileIdx + 2, right.second)
            }

            freeIdx = blocks.indexOfFirst { it is Free }
            fileIdx = blocks.indexOfLast { it is File }
        }

        return blocks
            .flatMap { b -> List(b.length) { if (b is File) b.id.toLong() else 0L } }
            .mapIndexed { i, id -> id * i }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val blocks = input.first().toBlocks().toMutableList()
        var freeIdx = 1
        while (freeIdx != -1) {
            val free = blocks[freeIdx]
            val fileIdx = blocks.indexOfLast { it is File && it.length <= free.length }

            if (fileIdx > freeIdx) {
                val file = blocks[fileIdx]
                tryMergeToFree(file as File, free as Free).let { (left, right) ->
                    blocks[freeIdx] = left.first
                    blocks.add(freeIdx + 1, left.second)

                    blocks[fileIdx + 1] = right
                }
            }

            freeIdx = (freeIdx + 1 .. blocks.lastIndex).find { blocks[it] is Free }?: -1
        }

        return blocks
            .flatMap { b -> List(b.length) { if (b is File) b.id.toLong() else 0L } }
            .mapIndexed { i, id -> id * i }
            .sum()
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
