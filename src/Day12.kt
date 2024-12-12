fun main() {
    operator fun List<String>.get(x: Int, y: Int) = getOrNull(x)?.getOrNull(y)
    operator fun List<String>.get(p: Pair<Int, Int>) = get(p.first, p.second)

    data class Plot(val x: Int, val y: Int, val plant: Char)
    fun Plot.neighbours(grid: List<String>) = listOf((x - 1 to y), (x + 1 to y), (x to y - 1), (x to y + 1))
        .filter { (x, y) -> x in grid.indices && y in grid[0].indices }
        .map { (x, y) -> Plot(x, y, grid[x][y]) }

    data class Region(val plant: Char, val area: Int = 0, val perimeter: Int = 0, val plots: List<Plot> = emptyList()) {
        fun corners(i: List<String>): Int {
            return plots.sumOf { p ->
                listOf(-1 to -1, 1 to -1, -1 to 1, 1 to 1).sumOf { (dx, dy) ->
                    val outer = if (i[p.x + dx, p.y] != p.plant && i[p.x, p.y + dy] != p.plant) 1 else 0
                    val inner = if (i[p.x + dx, p.y] == p.plant && i[p.x, p.y + dy] == p.plant && i[p.x + dx, p.y + dy] != p.plant) 1 else 0
                    outer + inner
                }
            }
        }
    }

    fun growRegion(plot: Plot, region: Region, visited: MutableSet<Plot>, grid: List<String>): Region {
        visited += plot
        val neighbours = plot.neighbours(grid)
        val perimeter = 4 - neighbours.size
        return neighbours.fold(Region(region.plant, region.area + 1, region.perimeter + perimeter, region.plots + plot)) { acc, neighbour ->
            when {
                neighbour.plant != plot.plant -> Region(region.plant, acc.area, acc.perimeter + 1, acc.plots)
                neighbour !in visited -> growRegion(neighbour, acc, visited, grid)
                else -> acc
            }
        }
    }

    fun findRegions(plots: List<Plot>, visited: MutableSet<Plot>, input: List<String>): List<Region> {
        return plots
            .mapNotNull { plot ->
                if (plot in visited) return@mapNotNull null
                growRegion(plot, Region(plot.plant), visited, input)
            }
    }

    fun part1(input: List<String>): Int {
        val plots = input.flatMapIndexed { x, row -> row.mapIndexed { y, plant -> Plot(x, y, plant) } }
        val regions = findRegions(plots, mutableSetOf(), input)
        return regions.sumOf { region -> region.area * region.perimeter }
    }

    fun part2(input: List<String>): Int {
        val plots = input.flatMapIndexed { x, row -> row.mapIndexed { y, plant -> Plot(x, y, plant) } }
        val regions = findRegions(plots, mutableSetOf(), input)
        return regions.sumOf { region -> region.area * region.corners(input) }
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
